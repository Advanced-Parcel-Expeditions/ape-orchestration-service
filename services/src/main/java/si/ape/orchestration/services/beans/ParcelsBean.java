package si.ape.orchestration.services.beans;

import si.ape.orchestration.lib.Customer;
import si.ape.orchestration.lib.Parcel;
import si.ape.orchestration.lib.ParcelStatus;
import si.ape.orchestration.lib.requests.parcels.CreateParcelRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.util.List;

/**
 * The ParcelsBean class is a class which takes care of communication with the parcels microservice and provides
 * the parcels to the frontend application.
 */
@ApplicationScoped
public class ParcelsBean {

    @Inject
    private EntityManager em;

    /**
     * The viewParcels method is used to retrieve all the parcels.
     *
     * @return All the parcels.
     */
    public List<Parcel> viewParcels() {
        Client client = ClientBuilder.newClient();
        return client.target("http://dev.okeanos.mywire.org/parcels/v1/parcels")
                .request()
                .get(List.class);
    }

    /**
     * The viewParcel method is used to retrieve a specific parcel.
     *
     * @param parcelId The ID of the parcel.
     * @return The parcel.
     */
    public Parcel viewParcel(String parcelId) {
        Client client = ClientBuilder.newClient();
        return client.target("http://dev.okeanos.mywire.org/parcels/v1/parcels/")
                .queryParam("id", parcelId)
                .request()
                .get(Parcel.class);
    }

    /**
     * The createParcel method is used to create a new parcel.
     *
     * @param createParcelRequest The request containing the information about the parcel.
     */
    public void createParcel(CreateParcelRequest createParcelRequest) {
        Customer sender = em.find(Customer.class, createParcelRequest.getSenderId());
        Customer recipient = em.find(Customer.class, createParcelRequest.getRecipientId());
        ParcelStatus parcelStatus = em.find(ParcelStatus.class, 1);
        Parcel parcel = new Parcel();
        parcel.setSender(sender);
        parcel.setRecipient(recipient);
        parcel.setParcelStatus(parcelStatus);
        parcel.setWeight(createParcelRequest.getWeight());
        parcel.setHeight(createParcelRequest.getHeight());
        parcel.setWidth(createParcelRequest.getWidth());
        parcel.setDepth(createParcelRequest.getDepth());

        Client client = ClientBuilder.newClient();
        client.target("http://dev.okeanos.mywire.org/parcels/v1/parcels")
                .request()
                .post(Entity.json(parcel));
    }

}
