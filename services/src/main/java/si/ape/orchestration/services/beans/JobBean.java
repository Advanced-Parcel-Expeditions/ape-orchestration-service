package si.ape.orchestration.services.beans;

import org.json.JSONArray;
import org.json.JSONObject;
import si.ape.orchestration.lib.Job;
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
     * @param employeeId  The ID of the employee.
     * @param jobStatusId The ID of the job status.
     * @return The jobs of the employee with the specified status.
     */
    public List<Job> viewJobsWithStatus(Integer employeeId, Integer jobStatusId) {
        Client client = ClientBuilder.newClient();
        String viewJobsWithStatusQuery = this.viewJobsWithStatusQuery.replace("?", Integer.toString(employeeId));
        viewJobsWithStatusQuery = viewJobsWithStatusQuery.replace("!", Integer.toString(jobStatusId));
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

    public boolean createJob() {
        return false;
    }

    public boolean completeJob() {
        return false;
    }

    public boolean cancelJob() {
        return false;
    }

}
