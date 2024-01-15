package si.ape.orchestration.services.beans;

import si.ape.orchestration.lib.Street;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The LocationBean class is a class which takes care of communication with the location microservice and provides
 * the locations to the frontend application.
 */
@RequestScoped
public class LocationBean {

    /** The LocationBean's entity manager. */
    @Inject
    private EntityManager em;

    /**
     * The findStreetWithName method is used to retrieve all the streets with a specific name.
     *
     * @param name The name of the street.
     * @return All the streets with the specified name.
     */
    public List<Street> findStreetWithName(String name) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/api/locations/v1/locations/street-search/" + name)
                .request()
                .get();

        if (response.getStatus() == 200) {
            return response.readEntity(List.class);
        } else {
            return null;
        }
    }

}
