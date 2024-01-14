package si.ape.orchestration.services.beans;

import si.ape.orchestration.lib.Customer;
import si.ape.orchestration.lib.Employee;
import si.ape.orchestration.lib.Job;
import si.ape.orchestration.lib.Parcel;
import si.ape.orchestration.lib.requests.authentication.LoginRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterCustomerRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterEmployeeRequest;
import si.ape.orchestration.lib.requests.parcels.CreateParcelRequest;
import si.ape.orchestration.lib.responses.messaging.BranchStatisticsResponse;
import si.ape.orchestration.lib.responses.messaging.OrganizationStatisticsResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;


/**
 * The OrchestrationBean class is a class which takes care of communication with the other microservices. It
 * takes care of login and registration of users, and basically acts as a proxy between the other microservices
 * and the rest of the application. The use of this class is to provide a single point of entry for the frontend
 * application.
 *
 * The OrchestrationBean class works in such a way that it delegates the requests to the appropriate beans, which
 * handle the communication with the other microservices.
 */
@ApplicationScoped
public class OrchestrationBean {

    /** The OrchestrationBean's logger. */
    private Logger log = Logger.getLogger(OrchestrationBean.class.getName());

    /** The entity manager, which is used to communicate with the database. */
    @Inject
    private EntityManager em;

    /** The authentication bean, which is used to communicate with the authentication microservice. */
    @Inject
    private AuthenticationBean authenticationBean;

    /** The job bean, which is used to communicate with the jobs microservice. */
    @Inject
    private JobBean jobBean;

    /** The statistics bean, which is used to communicate with the statistics microservice. */
    @Inject
    private StatisticsBean statisticsBean;

    /** The parcels bean, which is used to communicate with the parcels microservice. */
    @Inject
    private ParcelsBean parcelsBean;

    /** The messaging bean, which is used to communicate with the messaging microservice. */
    @Inject
    private MessagingBean messagingBean;

    // Authentication microservice.

    /**
     * Delegates the login request to the authentication bean and returns the success.
     *
     * @param loginRequest The login request object.
     * @return True if the user was successfully logged in, false otherwise.
     */
    public String login(LoginRequest loginRequest) {
        return authenticationBean.login(loginRequest);
    }

    /**
     * Delegates the customer registration request to the authentication bean and returns the success.
     *
     * @param registerCustomerRequest The register customer request object.
     * @return True if the customer was successfully registered, false otherwise.
     */
    public boolean registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        return authenticationBean.registerCustomer(registerCustomerRequest);
    }

    /**
     * Delegates the employee registration request to the authentication bean and returns the success.
     *
     * @param registerEmployeeRequest The register employee request object.
     * @return True if the employee was successfully registered, false otherwise.
     */
    public boolean registerEmployee(RegisterEmployeeRequest registerEmployeeRequest) {
        return authenticationBean.registerEmployee(registerEmployeeRequest);
    }

    // Jobs microservice.

    public List<Job> viewJobs() {
        jobBean.viewJobs();
        return null;
    }

    public List<Job> viewJobsWithStatus() {
        jobBean.viewJobsWithStatus();
        return null;
    }

    public Job viewJobsOfBranch() {
        jobBean.viewJobsOfBranch();
        return null;
    }

    public Job createJob() {
        jobBean.createJob();
        return null;
    }

    public Job completeJob() {
        jobBean.completeJob();
        return null;
    }

    public Job cancelJob() {
        jobBean.cancelJob();
        return null;
    }


    // Statistics.

    /**
     * Delegates the organization statistics request to the statistics bean and returns the organization statistics.
     *
     * @return The overall organization statistics.
     */
    public OrganizationStatisticsResponse viewStatistics() {
        return statisticsBean.viewStatistics();
    }

    /**
     * Delegates the branch statistics request to the statistics bean and returns the branch statistics.
     *
     * @param branchId The ID of the branch.
     * @return The branch statistics.
     */
    public BranchStatisticsResponse viewStatisticsOfBranch(Integer branchId) {
        return statisticsBean.viewStatisticsOfBranch(branchId);
    }


    // Parcels microservice.

    /**
     * Delegates the view parcels request to the parcels bean and returns the list of parcels.
     *
     * @return The list of parcels.
     */
    public List<Parcel> viewParcels() {
        return parcelsBean.viewParcels();
    }

    /**
     * Delegates the view parcel request to the parcels bean and returns the parcel.
     *
     * @param parcelId The ID of the parcel.
     * @return The parcel.
     */
    public Parcel viewParcel(String parcelId) {
        return parcelsBean.viewParcel(parcelId);
    }

    /**
     * Delegates the create parcel request to the parcels bean.
     *
     * @param createParcelRequest The create parcel request object.
     */
    public void createParcel(CreateParcelRequest createParcelRequest) {
        parcelsBean.createParcel(createParcelRequest);
    }


    // Messaging microservice.

    public void viewConversations() {
        messagingBean.viewConversations();
    }

    public void viewMessagesInConversation() {
        messagingBean.viewMessagesInConversation();
    }

    public void sendMessage() {
        messagingBean.sendMessage();
    }

    public void createConversation() {
        messagingBean.createConversation();
    }

    public void addUserToConversation() {
        messagingBean.addUserToConversation();
    }


    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
