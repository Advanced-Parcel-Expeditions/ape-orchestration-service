package si.ape.orchestration.services.beans;

import si.ape.orchestration.lib.Customer;

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
 * The CustomerBean class is a class which takes care of communication with the customer microservice and provides
 * the customers to the frontend application.
 */
@RequestScoped
public class CustomerBean {

    /** The CustomerBean's entity manager. */
    @Inject
    private EntityManager em;

    /**
     * The findCustomersBySearchString method is used to retrieve all the customers with a specific name, surname or
     * username.
     *
     * @param searchString The name of the customer.
     * @return All the customers with the specified name.
     */
    public List<Customer> findCustomersBySearchString(String searchString) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/customer/v1/customer/" + searchString)
                .queryParam("searchString", searchString)
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

}
