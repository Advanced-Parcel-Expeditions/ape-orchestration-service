package si.ape.orchestration.services.beans;

import si.ape.orchestration.lib.Branch;
import si.ape.orchestration.lib.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class StaffBean {

    @Inject
    private EntityManager em;

    public Branch findBranchByName(String name) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8081/v1/branches/name/" + name).request().get();

        if (response.getStatus() == 200) {
            return response.readEntity(Branch.class);
        } else {
            return null;
        }
    }

    public List<Employee> findEmployeesOfBranch(Integer branchId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8082/v1/employees/branch/" + branchId).request().get();

        if (response.getStatus() == 200) {
            // Parse the response into a list of employees.
            return response.readEntity(List.class);
        } else {
            return null;
        }
    }

}
