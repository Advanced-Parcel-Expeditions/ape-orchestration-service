package si.ape.orchestration.services.beans;

import org.eclipse.microprofile.faulttolerance.Retry;
import si.ape.orchestration.lib.responses.statistics.BranchStatisticsResponse;
import si.ape.orchestration.lib.responses.statistics.OrganizationStatisticsResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * The StatisticsBean class is a class which takes care of communication with the statistics microservice and provides
 * the statistics to the frontend application.
 */
@RequestScoped
public class StatisticsBean {

    private Logger log = Logger.getLogger(StatisticsBean.class.getName());

    /**
     * The viewStatistics method is used to retrieve the statistics of the organization.
     *
     * @return The statistics of the organization.
     */
    @Retry(maxRetries = 3)
    public OrganizationStatisticsResponse viewStatistics() {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/statistics/v1/statistics")
                .request(MediaType.APPLICATION_JSON)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get();

        if (response.getStatus() == 200) {
            return response.readEntity(OrganizationStatisticsResponse.class);
        } else {
            return null;
        }
    }

    /**
     * The viewStatisticsOfBranch method is used to retrieve the statistics of a specific branch.
     *
     * @param branchId The ID of the branch.
     * @return The statistics of the branch.
     */
    @Retry(maxRetries = 3)
    public BranchStatisticsResponse viewStatisticsOfBranch(Integer branchId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/statistics/v1/statistics/branch/" + branchId)
                .request(MediaType.APPLICATION_JSON)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get();

        if (response.getStatus() == 200) {
            return response.readEntity(BranchStatisticsResponse.class);
        } else {
            return null;
        }
    }

}
