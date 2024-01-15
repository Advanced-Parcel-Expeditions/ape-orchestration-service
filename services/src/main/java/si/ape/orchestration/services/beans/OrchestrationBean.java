package si.ape.orchestration.services.beans;

import si.ape.orchestration.lib.*;
import si.ape.orchestration.lib.requests.authentication.LoginRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterCustomerRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterEmployeeRequest;
import si.ape.orchestration.lib.requests.job.CreateJobRequest;
import si.ape.orchestration.lib.requests.job.ViewJobsWithStatusRequest;
import si.ape.orchestration.lib.requests.parcels.CreateParcelRequest;
import si.ape.orchestration.lib.responses.statistics.BranchStatisticsResponse;
import si.ape.orchestration.lib.responses.statistics.OrganizationStatisticsResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class OrchestrationBean {

    /** The OrchestrationBean's logger. */
    private Logger log = Logger.getLogger(OrchestrationBean.class.getName());


    /** The authentication bean, which is used to communicate with the authentication microservice. */
    @Inject
    private AuthenticationBean authenticationBean;

    /** The job bean, which is used to communicate with the jobs microservice. */
    @Inject
    private JobBean jobBean;

    /** The staff bean, which is used to communicate with the staff microservice. */
    @Inject
    private StaffBean staffBean;

    @Inject
    private LocationBean locationBean;

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

    /**
     * Delegates the view jobs request to the jobs bean and returns the list of jobs.
     *
     * @param employeeId The ID of the employee.
     * @return The list of jobs.
     */
    public List<Job> viewJobs(Integer employeeId) {
        return jobBean.viewJobs(employeeId);
    }

    /**
     * Delegates the view jobs with status request to the jobs bean and returns the list of jobs.
     *
     * @param employeeId The ID of the employee.
     * @param statusId   The ID of the status.
     * @return The list of jobs.
     */
    public List<Job> viewJobsWithStatus(Integer employeeId, Integer statusId) {
        return jobBean.viewJobsWithStatus(employeeId, statusId);
    }

    /**
     * Delegates the view job request to the jobs bean and returns the parcel belonging to the job.
     *
     * @param jobId The ID of the job.
     * @return The parcel.
     */
    public Parcel viewParcelOfJob(Integer jobId) {
        return jobBean.viewParcelOfJob(jobId);
    }

    /**
     * Delegates the view job request to the jobs bean and returns the job.
     *
     * @return True if the job was created, false otherwise.
     */
    public boolean createJob(CreateJobRequest createJobRequest) {
        String parcelId = parcelsBean.createParcel(createJobRequest.getParcel());
        return jobBean.createJob(createJobRequest, parcelId);
    }

    /**
     * Delegates the complete job request to the jobs bean and returns the success.
     *
     * @param jobId The ID of the job.
     * @return True if the job was completed, false otherwise.
     */
    public boolean completeJob(Integer jobId) {
        return jobBean.completeJob(jobId);
    }

    /**
     * Delegates the cancel job request to the jobs bean and returns the success.
     *
     * @param jobId The ID of the job.
     * @return True if the job was cancelled, false otherwise.
     */
    public boolean cancelJob(Integer jobId) {
        return jobBean.cancelJob(jobId);
    }

    // Staff microservice.

    /**
     * Delegates the find branch by name request to the staff bean and returns the branch.
     *
     * @param name The name of the branch.
     * @return The branch.
     */
    public Branch findBranchByName(String name) {
        return staffBean.findBranchByName(name);
    }

    /**
     * Delegates the find employees of branch request to the staff bean and returns the list of employees.
     *
     * @param branchId The ID of the branch.
     * @return The list of employees.
     */
    public List<Employee> findEmployeesOfBranch(Integer branchId) {
        return staffBean.findEmployeesOfBranch(branchId);
    }

    // Location microservice.

    /**
     * Delegates the find street with name request to the location bean and returns the list of streets.
     *
     * @param name The name of the street.
     * @return The list of streets.
     */
    public List<Street> findStreetWithName(String name) {
        return locationBean.findStreetWithName(name);
    }

    // Statistics microservice.

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

    @Deprecated
    public void viewConversations() {
        messagingBean.viewConversations();
    }

    @Deprecated
    public void viewMessagesInConversation() {
        messagingBean.viewMessagesInConversation();
    }

    @Deprecated
    public void sendMessage() {
        messagingBean.sendMessage();
    }

    @Deprecated
    public void createConversation() {
        messagingBean.createConversation();
    }

    @Deprecated
    public void addUserToConversation() {
        messagingBean.addUserToConversation();
    }
}
