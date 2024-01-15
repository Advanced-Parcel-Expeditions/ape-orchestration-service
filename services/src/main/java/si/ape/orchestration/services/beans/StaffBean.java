package si.ape.orchestration.services.beans;

import si.ape.orchestration.lib.Branch;
import si.ape.orchestration.lib.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
public class StaffBean {

    public Branch findBranchByName(String name) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8081/v1/staff/branches/name/" + name).request().get();

        if (response.getStatus() == 200) {
            return response.readEntity(Branch.class);
        } else {
            return null;
        }
    }

    public List<Employee> findEmployeesOfBranch(Integer branchId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8082/v1/staff/staff?branchId=" + branchId).request().get();

        if (response.getStatus() == 200) {
            // Parse the response into a list of employees.
            return response.readEntity(List.class);
        } else {
            return null;
        }
    }

}
