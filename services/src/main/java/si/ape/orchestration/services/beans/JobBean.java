package si.ape.orchestration.services.beans;

import com.google.gson.Gson;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.json.JSONArray;
import org.json.JSONObject;
import si.ape.orchestration.lib.Job;
import si.ape.orchestration.lib.Parcel;
import si.ape.orchestration.lib.requests.job.CreateJobRequest;
import si.ape.orchestration.models.converters.JobConverter;
import si.ape.orchestration.models.entities.EmployeeEntity;
import si.ape.orchestration.models.entities.JobEntity;
import si.ape.orchestration.models.entities.JobStatusEntity;
import si.ape.orchestration.models.entities.JobTypeEntity;
import si.ape.orchestration.services.beans.graphql.ParcelConnection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * The JobBean class is a class which takes care of communication with the jobs microservice. It takes care of querying
 * the jobs microservice's GraphQL API and forwards the requests to the microservice. It also takes care of the responses
 * and forwards them to the client.
 */
@RequestScoped
@CircuitBreaker(failOn = {RuntimeException.class}, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
public class JobBean {

    @Inject
    private EntityManager em;

    /** The getJobsOfEmployee query. */
    private final String viewJobsQuery = "{"
            + "\"query\": \"query JobsOfEmployee { "
            + "jobsOfEmployee(employeeId: <set-employee-id>) { "
            + "totalCount "
            + "edges { "
            + "dateCompleted "
            + "dateCreated "
            + "id "
            + "jobStatus { "
            + "id "
            + "name "
            + "} "
            + "jobType { "
            + "id "
            + "name "
            + "} "
            + "} "
            + "} "
            + "}\", "
            + "\"variables\": {}"
            + "}";

    /** The getJobsOfEmployeeWithStatus query. */
    private final String viewJobsWithStatusQuery = "{\n" +
            "  \"query\": \"query JobsOfEmployeeWithStatus { jobsOfEmployeeWithStatus(employeeId: <set-employee-id>, status: <set-status-id>) { totalCount edges { dateCompleted dateCreated id jobStatus { id name } jobType { id name } } } }\",\n" +
            "  \"variables\": null\n" +
            "}";

    /** The viewParcelOfJob query. */
    private final String viewParcelOfJobQuery = "{\n" +
            "  \"query\": \"query ViewParcelOfJob { viewParcelOfJob(jobId: <set-job-id>) { totalCount edges { depth height id weight width parcelStatus { id name } recipient { companyName id name surname telNum street { streetName streetNumber city { code latitude longitude name country { code name } } } user { id password username role { id roleName } } } recipientStreet { streetName streetNumber city { code latitude longitude name country { code name } } } sender { companyName id name surname telNum street { streetName streetNumber city { code latitude longitude name country { code name } } } user { id password username role { id roleName } } } senderStreet { streetName streetNumber city { code latitude longitude name country { code name } } } } } }\",\n" +
            "  \"variables\": null\n" +
            "}";

    /** The createJob mutation. */
    private final String createJobMutation = "{\n" +
            "  \"query\": \"mutation CreateJob { createJob( jobRequest: { parcelId: \\\"<set-parcel-id>\\\", job: { jobType: { id: <set-job-type-id> }, staff: { id: <set-employee-id> } } }) { totalCount edges { dateCompleted dateCreated id } } }\",\n" +
            "  \"variables\": {}\n" +
            "}";

    /** The completeJob mutation. */
    private final String completeJobMutation = "{\n" +
            "  \"query\": \"mutation CompleteJob { completeJob(jobId: <set-job-id>) { totalCount edges { dateCompleted dateCreated id jobStatus { id name } jobType { id name } staff { id name surname } } } }\"\n" +
            "}";

    /** The linkJobAndParcel mutation. */
    private final String linkJobAndParcelMutation = "{\n" +
            "  \"query\": \"mutation LinkJobAndParcel { linkJobAndParcel(jobId: <set-job-id>, parcelId: \\\"<set-parcel-id>\\\") { totalCount edges { dateCompleted dateCreated id } } }\"\n" +
            "}";

    /** The cancelJob mutation. */
    private final String cancelJobMutation = "{\n" +
            "  \"query\": \"mutation CancelJob { cancelJob(jobId: <set-job-id>) { totalCount edges { dateCompleted dateCreated id jobStatus { id name } jobType { id name } } } }\"\n" +
            "}";

    /**
     * Queries the jobs microservice's GraphQL API and returns the jobs of the employee.
     *
     * @param employeeId The ID of the employee.
     * @return The jobs of the employee.
     */
    public List<Job> viewJobs(Integer employeeId) {

        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(viewJobsQuery.replace("<set-employee-id>", Integer.toString(employeeId))), Response.class);

        System.out.println("Response status: " + response.getStatus());

        if (response.getStatus() == 200) {
            String responseJson = response.readEntity(String.class);
            JSONObject responseJsonObject = new JSONObject(responseJson);
            JSONObject jobConnection = responseJsonObject.getJSONObject("data").getJSONObject("jobsOfEmployee");
            long totalCount = jobConnection.getLong("totalCount");
            JSONArray edges = jobConnection.getJSONArray("edges");

            List<JobEntity> jobEntities = new ArrayList<>();
            List<Job> jobs = new ArrayList<>();

            for (int i = 0; i < edges.length(); i++) {
                JSONObject edge = edges.getJSONObject(i);
                JSONObject jobStatus = edge.getJSONObject("jobStatus");
                JSONObject jobType = edge.getJSONObject("jobType");

                System.out.println(edge.toString());

                String dateCompleted = "null";
                if (!edge.isNull("dateCompleted")) {
                    dateCompleted = edge.getString("dateCompleted");
                }
                String dateCreated = edge.getString("dateCreated");
                Integer id = edge.getInt("id");

                System.out.println("JOBSTATUSID: " + jobStatus.getInt("id"));

                JobEntity job = new JobEntity();
                job.setJobStatus(em.find(JobStatusEntity.class, jobStatus.getInt("id")));
                job.setJobType(em.find(JobTypeEntity.class, jobType.getInt("id")));
                if (!dateCompleted.equals("null")) {
                    job.setDateCompleted(Instant.parse(dateCompleted));
                }
                job.setStaff(em.find(EmployeeEntity.class, employeeId));
                job.setDateCreated(Instant.parse(dateCreated.strip() + "Z"));
                job.setId(id);

                jobEntities.add(job);
            }
            return jobEntities.stream().map(JobConverter::toDto).toList();
        } else {
            return null;
        }

    }

    /**
     * Queries the jobs microservice's GraphQL API and returns the jobs of the employee with the specified status.
     *
     * @param employeeId The ID of the employee.
     * @param statusId   The ID of the status.
     * @return The jobs of the employee with the specified status.
     */
    public List<Job> viewJobsWithStatus(Integer employeeId, Integer statusId) {
        Client client = ClientBuilder.newClient();
        String viewJobsWithStatusQuery = this.viewJobsWithStatusQuery.replace("<set-employee-id>", Integer.toString(employeeId));
        viewJobsWithStatusQuery = viewJobsWithStatusQuery.replace("<set-status-id>", Integer.toString(statusId));
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(viewJobsWithStatusQuery), Response.class);

        System.out.println("Response status: " + response.getStatus());

        if (response.getStatus() == 200) {
            String responseJson = response.readEntity(String.class);
            JSONObject responseJsonObject = new JSONObject(responseJson);
            JSONObject jobConnection = responseJsonObject.getJSONObject("data").getJSONObject("jobsOfEmployeeWithStatus");
            long totalCount = jobConnection.getLong("totalCount");
            JSONArray edges = jobConnection.getJSONArray("edges");

            List<JobEntity> jobEntities = new ArrayList<>();
            List<Job> jobs = new ArrayList<>();

            for (int i = 0; i < edges.length(); i++) {
                JSONObject edge = edges.getJSONObject(i);
                JSONObject jobStatus = edge.getJSONObject("jobStatus");
                JSONObject jobType = edge.getJSONObject("jobType");
                String dateCompleted = "null";

                if (!edge.isNull("dateCompleted")) {
                    dateCompleted = edge.getString("dateCompleted");
                }
                String dateCreated = edge.getString("dateCreated");
                Integer id = edge.getInt("id");

                JobEntity job = new JobEntity();
                job.setJobStatus(em.find(JobStatusEntity.class, jobStatus.getInt("id")));
                job.setJobType(em.find(JobTypeEntity.class, jobType.getInt("id")));
                if (!dateCompleted.equals("null")) {
                    job.setDateCompleted(Instant.parse(dateCompleted));
                }
                job.setStaff(em.find(EmployeeEntity.class, employeeId));
                job.setDateCreated(Instant.parse(dateCreated.strip() + "Z"));
                job.setId(id);

                jobEntities.add(job);
            }
            return jobEntities.stream().map(JobConverter::toDto).toList();
        } else {
            return null;
        }
    }

    /**
     * Queries the jobs microservice's GraphQL API and returns the parcel belonging to the job.
     *
     * @param jobId The ID of the job.
     * @return The parcel.
     */
    public Parcel viewParcelOfJob(Integer jobId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(viewParcelOfJobQuery.replace("<set-job-id>", Integer.toString(jobId))), Response.class);

        System.out.println("Response status: " + response.getStatus());

        if (response.getStatus() == 200) {
            String json = response.readEntity(String.class);
            JSONObject jsonObject = new JSONObject(json);
            System.out.println(jsonObject.toString());
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject viewParcelOfJob = data.getJSONObject("viewParcelOfJob");

            // Deserialize the viewParcelOfJob object into a ParcelConnection object.
            Gson gson = new Gson();
            ParcelConnection parcelConnection = gson.fromJson(viewParcelOfJob.toString(), ParcelConnection.class);
            if (parcelConnection.getEdges().isEmpty()) {
                return null;
            }
            Parcel parcel = parcelConnection.getEdges().get(0);
            return parcel;
        } else {
            return null;
        }
    }

    /**
     * Queries the jobs microservice's GraphQL API and creates a new job. Additionally, it links the job with the
     * specified parcel.
     *
     * @param createJobRequest The request containing the information about the job.
     * @param parcelId         The ID of the parcel.
     * @return True if the job was created, false otherwise.
     */
    public boolean createJob(CreateJobRequest createJobRequest, String parcelId) {
        Client client = ClientBuilder.newClient();
        String createJobMutation = this.createJobMutation.replace("<set-parcel-id>", parcelId);
        createJobMutation = createJobMutation.replace("<set-job-type-id>", Integer.toString(1));
        createJobMutation = createJobMutation.replace("<set-employee-id>", Integer.toString(createJobRequest.getEmployeeId()));
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(createJobMutation), Response.class);

        System.out.println("Mutation: " + createJobMutation);

        System.out.println("Job creation");
        System.out.println("Response status: " + response.getStatus());

        return response.getStatus() == 200;
    }

    /**
     * Queries the jobs microservice's GraphQL API and completes the job.
     *
     * @param jobId The ID of the job.
     * @return True if the job was completed, false otherwise.
     */
    @Bulkhead(value = 40, waitingTaskQueue = 40)
    public boolean completeJob(Integer jobId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(completeJobMutation.replace("<set-job-id>", Integer.toString(jobId))), Response.class);

        if (response.getStatus() == 200) {
            return true;
        }

        return false;
    }

    /**
     * Queries the jobs microservice's GraphQL API and cancels the job.
     *
     * @param jobId The ID of the job.
     * @return True if the job was cancelled, false otherwise.
     */
    public boolean cancelJob(Integer jobId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(cancelJobMutation.replace("<set-job-id>", Integer.toString(jobId))), Response.class);

        if (response.getStatus() == 200) {
            return true;
        }

        return false;
    }

}
