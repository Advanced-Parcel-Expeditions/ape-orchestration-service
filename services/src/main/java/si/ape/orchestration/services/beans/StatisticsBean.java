package si.ape.orchestration.services.beans;

import si.ape.orchestration.lib.responses.statistics.BranchStatisticsResponse;
import si.ape.orchestration.lib.responses.statistics.OrganizationStatisticsResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * The StatisticsBean class is a class which takes care of communication with the statistics microservice and provides
 * the statistics to the frontend application.
 */
@RequestScoped
public class StatisticsBean {

    /**
     * The viewStatistics method is used to retrieve the statistics of the organization.
     *
     * @return The statistics of the organization.
     */
    public OrganizationStatisticsResponse viewStatistics() {
        Client client = ClientBuilder.newClient();
        return client.target("http://dev.okeanos.mywire.org/statistics/v1/statistics")
                .request()
                .get(OrganizationStatisticsResponse.class);
    }

    /**
     * The viewStatisticsOfBranch method is used to retrieve the statistics of a specific branch.
     *
     * @param branchId The ID of the branch.
     * @return The statistics of the branch.
     */
    public BranchStatisticsResponse viewStatisticsOfBranch(Integer branchId) {
        Client client = ClientBuilder.newClient();
        return client.target("http://dev.okeanos.mywire.org/statistics/v1/statistics/branch/" + branchId)
                .request()
                .get(BranchStatisticsResponse.class);
    }

}
