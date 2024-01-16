package si.ape.orchestration.services.beans;

import org.eclipse.microprofile.faulttolerance.Retry;
import si.ape.orchestration.lib.Branch;
import si.ape.orchestration.lib.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The StaffBean class is a class which takes care of communication with the staff microservice and provides
 * the staff to the frontend application.
 */
@RequestScoped
public class StaffBean {

    /**
     * Find a branch by its name.
     *
     * @param name The name of the branch.
     * @return The branch with the specified name.
     */
    @Retry(maxRetries = 3)
    public List<Branch> findBranchByName(String name) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/staff/v1/staff/branches/" + name)
                .request(MediaType.APPLICATION_JSON)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get();

        if (response.getStatus() == 200) {
            return response.readEntity(List.class);
        } else {
            return null;
        }
    }

    /**
     * Find all the employees of a branch.
     *
     * @param branchId The ID of the branch.
     * @return All the employees of the branch.
     */
    @Retry(maxRetries = 3)
    public List<Employee> findEmployeesOfBranch(Integer branchId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/staff/v1/staff?branchId="  + branchId)
                .request(MediaType.APPLICATION_JSON)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get();

        if (response.getStatus() == 200) {
            // Parse the response into a list of employees.
            return response.readEntity(List.class);
        } else {
            return null;
        }
    }

}
