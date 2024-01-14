package si.ape.orchestration.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.ape.orchestration.lib.Customer;
import si.ape.orchestration.lib.Employee;
import si.ape.orchestration.lib.requests.authentication.LoginRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterCustomerRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterEmployeeRequest;
import si.ape.orchestration.services.beans.OrchestrationBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;


@ApplicationScoped
@Path("/orchestration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrchestrationResource {

    private final Logger log = Logger.getLogger(OrchestrationResource.class.getName());

    @Inject
    private OrchestrationBean orchestrationBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Attempts to log in the user.", summary = "Login user")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "User has been successfully logged in."
            ),
            @APIResponse(responseCode = "401",
                    description = "User has not been successfully logged in."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        try {
            String rawToken = orchestrationBean.login(loginRequest);
            if (rawToken != null) {
                return Response.ok(rawToken).header("Authorization", rawToken).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Attempts to register a customer.", summary = "Register customer")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Customer has been registered successfully."
            ),
            @APIResponse(responseCode = "400",
                    description = "Customer has not been registered successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @POST
    @Path("/register-customer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        try {
            if (orchestrationBean.registerCustomer(registerCustomerRequest)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Attempts to register an employee.", summary = "Register employee")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Employee has been registered successfully."
            ),
            @APIResponse(responseCode = "400",
                    description = "Employee has not been registered successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @POST
    @Path("/register-employee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerEmployee(RegisterEmployeeRequest registerEmployeeRequest) {
        try {
            if (orchestrationBean.registerEmployee(registerEmployeeRequest)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
