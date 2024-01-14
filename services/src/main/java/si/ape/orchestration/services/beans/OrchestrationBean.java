package si.ape.orchestration.services.beans;

import si.ape.orchestration.lib.Customer;
import si.ape.orchestration.lib.Employee;
import si.ape.orchestration.lib.Job;
import si.ape.orchestration.lib.requests.authentication.LoginRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterCustomerRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterEmployeeRequest;

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

    public void viewStatistics() {
        statisticsBean.viewStatistics();
    }

    public void viewStatisticsOfBranch() {
        statisticsBean.viewStatisticsOfBranch();
    }


    // Parcels microservice.

    public void viewParcels() {
        parcelsBean.viewParcels();
    }

    public void createParcel() {
        parcelsBean.createParcel();
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
