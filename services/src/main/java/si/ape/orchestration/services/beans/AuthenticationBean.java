package si.ape.orchestration.services.beans;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import si.ape.orchestration.lib.requests.authentication.LoginRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterCustomerRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterEmployeeRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The AuthenticationBean class is a class which takes care of communication with the authentication microservice. It
 * takes care of login and registration of users, and basically acts as a proxy between the authentication microservice
 * and the rest of the application.
 */
@RequestScoped
@CircuitBreaker(failOn = {IllegalStateException.class}, requestVolumeThreshold = 4)
public class AuthenticationBean {

    /**
     * Attempts to log in the user. If the user is successfully logged in, the method attempts to extract the JWT token
     * from the response and forwards it to the client.
     *
     * @param loginRequest The login request object.
     * @return The JWT token if the user was successfully logged in, null otherwise.
     */
    public String login(LoginRequest loginRequest) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/authentication/v1/auth/login")
                .request()
                .post(Entity.json(loginRequest), Response.class);

        if (response.getStatus() == 200) {
            return response.getHeaderString("Authorization");
        } else if (response.getStatus() == 500) {
            throw new IllegalStateException("Internal server error.");
        } else {
            return null;
        }
    }

    /**
     * Attempts to register a customer.
     *
     * @param registerCustomerRequest The register customer request object.
     * @return True if the customer was successfully registered, false otherwise.
     * @throws IllegalStateException If the authentication microservice returns an internal server error.
     */
    public boolean registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/authentication/v1/auth/register/customer")
                .request()
                .post(Entity.json(registerCustomerRequest), Response.class);

        if (response.getStatus() == 200) {
            return true;
        } else if (response.getStatus() == 500) {
            throw new IllegalStateException("Internal server error.");
        } else {
            return false;
        }
    }

    /**
     * Attempts to register an employee.
     *
     * @param registerEmployeeRequest The register employee request object.
     * @return True if the employee was successfully registered, false otherwise.
     * @throws IllegalStateException If the authentication microservice returns an internal server error.
     */
    public boolean registerEmployee(RegisterEmployeeRequest registerEmployeeRequest) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://dev.okeanos.mywire.org/authentication/v1/auth/register/employee")
                .request()
                .post(Entity.json(registerEmployeeRequest), Response.class);

        if (response.getStatus() == 200) {
            return true;
        } else if (response.getStatus() == 500) {
            throw new IllegalStateException("Internal server error.");
        } else {
            return false;
        }
    }

}
