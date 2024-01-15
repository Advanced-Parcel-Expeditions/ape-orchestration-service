package si.ape.orchestration.services.beans;

import org.json.JSONArray;
import org.json.JSONObject;
import si.ape.orchestration.lib.Job;
import si.ape.orchestration.lib.Parcel;
import si.ape.orchestration.lib.requests.job.CreateJobRequest;
import si.ape.orchestration.lib.requests.job.ViewJobsWithStatusRequest;
import si.ape.orchestration.models.converters.JobConverter;
import si.ape.orchestration.models.entities.JobEntity;
import si.ape.orchestration.models.entities.JobStatusEntity;
import si.ape.orchestration.models.entities.JobTypeEntity;

import javax.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
public class JobBean {

    @Inject
    private EntityManager em;

    /** The getJobsOfEmployee query. */
    private final String viewJobsQuery = "query JobsOfEmployee {\n" +
            "    jobsOfEmployee(employeeId: ?) {\n" +
            "        totalCount\n" +
            "        edges {\n" +
            "            dateCompleted\n" +
            "            dateCreated\n" +
            "            id\n" +
            "            jobStatus {\n" +
            "                id\n" +
            "                name\n" +
            "            }\n" +
            "            jobType {\n" +
            "                id\n" +
            "                name\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}\n";

    /** The getJobsOfEmployeeWithStatus query. */
    private final String viewJobsWithStatusQuery = "query JobsOfEmployeeWithStatus {\n" +
            "    jobsOfEmployeeWithStatus(employeeId: ?, status: !) {\n" +
            "        totalCount\n" +
            "        edges {\n" +
            "            dateCompleted\n" +
            "            dateCreated\n" +
            "            id\n" +
            "            jobStatus {\n" +
            "                id\n" +
            "                name\n" +
            "            }\n" +
            "            jobType {\n" +
            "                id\n" +
            "                name\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}\n";

    /** The viewParcelOfJob query. */
    private final String viewParcelOfJobQuery = "";

    /** The createJob mutation. */
    private final String createJobMutation = "";

    /** The completeJob mutation. */
    private final String completeJobMutation = "";

    /** The linkJobAndParcel mutation. */
    private final String linkJobAndParcelMutation = "";

    /** The cancelJob mutation. */
    private final String cancelJobMutation = "";

    /**
     * Queries the jobs microservice's GraphQL API and returns the jobs of the employee.
     *
     * @param employeeId The ID of the employee.
     * @return The jobs of the employee.
     */
    public List<Job> viewJobs(Integer employeeId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/v1/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(viewJobsQuery.replace("?", Integer.toString(employeeId))), Response.class);

        if (response.getStatus() == 200) {
            String responseJson = response.readEntity(String.class);
            JSONObject responseJsonObject = new JSONObject(responseJson);
            JSONObject jobConnection = responseJsonObject.getJSONObject("data").getJSONObject("jobsOfEmployee");
            long totalCount = jobConnection.getLong("totalCount");
            JSONArray edges = jobConnection.getJSONArray("edges");

            List<JobEntity> jobEntities = new ArrayList<>();

            for (int i = 0; i < edges.length(); i++) {
                JSONObject edge = edges.getJSONObject(i);
                JSONObject jobStatus = edge.getJSONObject("jobStatus");
                JSONObject jobType = edge.getJSONObject("jobType");
                String dateCompleted = edge.getString("dateCompleted");
                String dateCreated = edge.getString("dateCreated");
                Integer id = edge.getInt("id");

                JobEntity job = new JobEntity();
                job.setJobStatus(em.find(JobStatusEntity.class, jobStatus.getInt("id")));
                job.setJobType(em.find(JobTypeEntity.class, jobType.getInt("id")));
                if (!dateCompleted.equals("null")) {
                    job.setDateCompleted(Instant.parse(dateCompleted));
                }
                job.setDateCreated(Instant.parse(dateCreated));
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
        String viewJobsWithStatusQuery = this.viewJobsWithStatusQuery.replace("?", Integer.toString(employeeId));
        viewJobsWithStatusQuery = viewJobsWithStatusQuery.replace("!", Integer.toString(statusId));
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/v1/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(viewJobsWithStatusQuery), Response.class);

        if (response.getStatus() == 200) {
            String responseJson = response.readEntity(String.class);
            JSONObject responseJsonObject = new JSONObject(responseJson);
            JSONObject jobConnection = responseJsonObject.getJSONObject("data").getJSONObject("jobsOfEmployeeWithStatus");
            long totalCount = jobConnection.getLong("totalCount");
            JSONArray edges = jobConnection.getJSONArray("edges");

            List<JobEntity> jobEntities = new ArrayList<>();

            for (int i = 0; i < edges.length(); i++) {
                JSONObject edge = edges.getJSONObject(i);
                JSONObject jobStatus = edge.getJSONObject("jobStatus");
                JSONObject jobType = edge.getJSONObject("jobType");
                String dateCompleted = edge.getString("dateCompleted");
                String dateCreated = edge.getString("dateCreated");
                Integer id = edge.getInt("id");

                JobEntity job = new JobEntity();
                job.setJobStatus(em.find(JobStatusEntity.class, jobStatus.getInt("id")));
                job.setJobType(em.find(JobTypeEntity.class, jobType.getInt("id")));
                if (!dateCompleted.equals("null")) {
                    job.setDateCompleted(Instant.parse(dateCompleted));
                }
                job.setDateCreated(Instant.parse(dateCreated));
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
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/v1/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(viewParcelOfJobQuery.replace("?", Integer.toString(jobId))), Response.class);

        if (response.getStatus() == 200) {
            String responseJson = response.readEntity(String.class);
            JSONObject responseJsonObject = new JSONObject(responseJson);
            JSONObject job = responseJsonObject.getJSONObject("data").getJSONObject("job");
            JSONObject parcel = job.getJSONObject("parcel");
            // TODO: Fix this after adding endpoints to the jobs microservice.
            Integer depth = parcel.getInt("depth");
            Integer height = parcel.getInt("height");
            String id = parcel.getString("id");
            Double weight = parcel.getDouble("weight");
            Integer width = parcel.getInt("width");

            Parcel parcelDto = new Parcel();
            parcelDto.setDepth(depth);
            parcelDto.setHeight(height);
            parcelDto.setId(id);
            parcelDto.setWeight(weight);
            parcelDto.setWidth(width);

            return parcelDto;
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
        Integer employeeId = createJobRequest.getEmployeeId();

        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/v1/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(createJobMutation.replace("?", Integer.toString(employeeId))), Response.class);

        if (response.getStatus() == 200) {
            Integer jobId = response.readEntity(Integer.class);

            client = ClientBuilder.newClient();
            response = client.target("http://dev.okeanos.mywire.org/jobs/v1/graphql")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(linkJobAndParcelMutation.replace("?", Integer.toString(jobId)).replace("!", parcelId)), Response.class);

            if (response.getStatus() == 200) {
                return true;
            }

            return false;
        }

        return false;
    }

    /**
     * Queries the jobs microservice's GraphQL API and completes the job.
     *
     * @param jobId The ID of the job.
     * @return True if the job was completed, false otherwise.
     */
    public boolean completeJob(Integer jobId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/v1/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(completeJobMutation.replace("?", Integer.toString(jobId))), Response.class);

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
        Response response = client.target("http://dev.okeanos.mywire.org/jobs/v1/graphql")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(cancelJobMutation.replace("?", Integer.toString(jobId))), Response.class);

        if (response.getStatus() == 200) {
            return true;
        }

        return false;
    }

}
