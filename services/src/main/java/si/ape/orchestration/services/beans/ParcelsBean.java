package si.ape.orchestration.services.beans;

import org.eclipse.microprofile.faulttolerance.Retry;
import si.ape.orchestration.lib.Customer;
import si.ape.orchestration.lib.Parcel;
import si.ape.orchestration.lib.ParcelStatus;
import si.ape.orchestration.lib.Street;
import si.ape.orchestration.lib.requests.parcels.CreateParcelRequest;
import si.ape.orchestration.models.converters.CustomerConverter;
import si.ape.orchestration.models.converters.ParcelStatusConverter;
import si.ape.orchestration.models.entities.CustomerEntity;
import si.ape.orchestration.models.entities.ParcelStatusEntity;
import si.ape.orchestration.models.entities.StreetEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;

/**
 * The ParcelsBean class is a class which takes care of communication with the parcels microservice and provides
 * the parcels to the frontend application.
 */
@RequestScoped
public class ParcelsBean {

    @Inject
    private EntityManager em;

    /**
     * The viewParcels method is used to retrieve all the parcels where the customer is the sender.
     *
     * @return All the parcels.
     */
    @Retry(maxRetries = 3)
    public List<Parcel> viewParcelsAsSender(Integer customerId) {
        Client client = ClientBuilder.newClient();
        Response response =  client.target("http://dev.okeanos.mywire.org/api/parcels/v1/parcels")
                .queryParam("senderId", customerId)
                .request(MediaType.APPLICATION_JSON)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get();

        System.out.println(response.getStatus());

        if (response.getStatus() == 200) {
            return response.readEntity(List.class);
        } else {
            return null;
        }
    }

    /**
     * The viewParcels method is used to retrieve all the parcels where the customer is the recipient.
     *
     * @return All the parcels.
     */
    @Retry(maxRetries = 3)
    public List<Parcel> viewParcelsAsRecipient(Integer customerId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/api/parcels/v1/parcels")
                .queryParam("recipientId", customerId)
                .request(MediaType.APPLICATION_JSON)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get();

        System.out.println(response.getStatus());

        if (response.getStatus() == 200) {
            return response.readEntity(List.class);
        } else {
            return null;
        }
    }

    /**
     * The viewParcel method is used to retrieve a specific parcel.
     *
     * @param parcelId The ID of the parcel.
     * @return The parcel.
     */
    @Retry(maxRetries = 3)
    public Parcel viewParcel(String parcelId) {
        Client client = ClientBuilder.newClient();
        Response response =  client.target("http://dev.okeanos.mywire.org/api/parcels/v1/parcels")
                .queryParam("id", parcelId)
                .request()
                .get();

        if (response.getStatus() == 200) {
            List<Parcel> parcels = response.readEntity(List.class);
            if (parcels != null && !parcels.isEmpty()) {
                return parcels.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * The createParcel method is used to create a new parcel.
     *
     * @param createParcelRequest The request containing the information about the parcel.
     */
    public String createParcel(CreateParcelRequest createParcelRequest) {
        CustomerEntity senderEntity = em.find(CustomerEntity.class, createParcelRequest.getSenderId());
        CustomerEntity recipientEntity = em.find(CustomerEntity.class, createParcelRequest.getRecipientId());
        Customer sender = CustomerConverter.toDto(senderEntity);
        Customer recipient = CustomerConverter.toDto(recipientEntity);
        Street senderStreet = sender.getStreet();
        Street recipientStreet = recipient.getStreet();
        ParcelStatusEntity parcelStatusEntity = em.find(ParcelStatusEntity.class, 1);
        ParcelStatus parcelStatus = ParcelStatusConverter.toDto(parcelStatusEntity);

        System.out.println("Parcel status: " + parcelStatus.getId());
        System.out.println("Parcel status name: " + parcelStatus.getName());

        Parcel parcel = new Parcel();
        parcel.setId(generateParcelId(sender.getStreet().getCity().getCountry().getCode()));
        parcel.setSender(sender);
        parcel.setSenderStreet(senderStreet);
        parcel.setRecipient(recipient);
        parcel.setRecipientStreet(recipientStreet);
        parcel.setParcelStatus(parcelStatus);
        parcel.setWeight(createParcelRequest.getWeight());
        parcel.setHeight(createParcelRequest.getHeight());
        parcel.setWidth(createParcelRequest.getWidth());
        parcel.setDepth(createParcelRequest.getDepth());

        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/api/parcels/v1/parcels")
                .request()
                .post(Entity.json(parcel), Response.class);

        System.out.println(response.getStatus());

        if (response.getStatus() == 200) {
            // TODO: Deserialize the parcel
            return parcel.getId();
        }
        return null;
    }

    private String generateParcelId(String senderCountryCode) {
        StringBuilder idBuilder = new StringBuilder();
        Integer identicalIds = 0;

        do {
            idBuilder = new StringBuilder(senderCountryCode);
            idBuilder.append(generateRandomAlphanumericString(5));
        } while (identicalIds > 0);

        return idBuilder.toString();
    }

    private String generateRandomAlphanumericString(int size) {
        final int lowerLimit = 48; // Character '0'.
        final int upperLimit = 122; // Character 'z'.

        return new Random()
                .ints(lowerLimit, upperLimit)
                // Leave out non-alphanumeric characters.
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
