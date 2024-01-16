package si.ape.orchestration.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.ape.orchestration.lib.*;
import si.ape.orchestration.lib.requests.authentication.LoginRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterCustomerRequest;
import si.ape.orchestration.lib.requests.authentication.RegisterEmployeeRequest;
import si.ape.orchestration.lib.requests.job.CreateJobRequest;
import si.ape.orchestration.services.beans.OrchestrationBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
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

    // Jobs.

    @Operation(description = "Attempts to retrieve jobs of an employee.", summary = "View jobs")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Jobs have been returned successfully."
            ),
            @APIResponse(responseCode = "404",
                    description = "Jobs have not been returned successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @GET
    @Path("/jobs/{employeeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewJobs(@PathParam("employeeId") Integer employeeId) {
        try {
            List<Job> jobs = orchestrationBean.viewJobs(employeeId);
            if (jobs != null) {
                return Response.ok(jobs).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Attempts to retrieve jobs of an employee with the specified status.", summary = "View jobs with status")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Job has been returned successfully."
            ),
            @APIResponse(responseCode = "404",
                    description = "Job has not been returned successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @GET
    @Path("/jobs/{employeeId}/status/{statusId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewJobsWithStatus(@PathParam("employeeId") Integer employeeId, @PathParam("statusId") Integer statusId) {
        try {
            List<Job> jobs = orchestrationBean.viewJobsWithStatus(employeeId, statusId);
            if (jobs != null) {
                return Response.ok(jobs).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Attempts to retrieve a parcel belonging to a job.", summary = "View parcel of job")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Parcel has been returned successfully."
            ),
            @APIResponse(responseCode = "404",
                    description = "Parcel has not been returned successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @GET
    @Path("/job-parcel/{jobId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewParcelOfJob(@PathParam("jobId") Integer jobId) {
        try {
            Parcel parcel = orchestrationBean.viewParcelOfJob(jobId);
            if (parcel != null) {
                return Response.ok(parcel).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Attempts to create a job with type 1.", summary = "Create job")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Job has been created successfully."
            ),
            @APIResponse(responseCode = "400",
                    description = "Job has not been created successfully. The employee's role is not 'Order confirmation specialist'."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @POST
    @Path("/create-job")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJob(CreateJobRequest createJobRequest) {
        try {
            if (orchestrationBean.createJob(createJobRequest)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Attempts to complete the job.", summary = "Complete job")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Job has been completed successfully."
            ),
            @APIResponse(responseCode = "400",
                    description = "Job has not been completed successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @POST
    @Path("/complete-job")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response completeJob(Integer jobId) {
        try {
            if (orchestrationBean.completeJob(jobId)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Attempts to cancel the job.", summary = "Cancel job")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Job has been cancelled successfully."
            ),
            @APIResponse(responseCode = "400",
                    description = "Job has not been cancelled successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @POST
    @Path("/cancel-job")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelJob(Integer jobId) {
        try {
            if (orchestrationBean.cancelJob(jobId)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Staff.

    @Operation(description = "Attempts to retrieve a branch by its name.", summary = "View branch by name")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Branch has been returned successfully."
            ),
            @APIResponse(responseCode = "404",
                    description = "Branch has not been returned successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @GET
    @Path("/branch/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBranchByName(@PathParam("name") String name) {
        try {
            List<Branch> branches = orchestrationBean.findBranchByName(name);
            if (branches != null) {
                return Response.ok(branches).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Attempts to retrieve employees of a branch.", summary = "View employees of branch")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Employees have been returned successfully."
            ),
            @APIResponse(responseCode = "404",
                    description = "Employees have not been returned successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @GET
    @Path("/employees/{branchId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findEmployeesOfBranch(@PathParam("branchId") Integer branchId) {
        try {
            List<Employee> employees = orchestrationBean.findEmployeesOfBranch(branchId);
            if (employees != null) {
                return Response.ok(employees).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Locations.
    @Operation(description = "Attempts to retrieve employees of a branch.", summary = "View employees of branch")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Employees have been returned successfully."
            ),
            @APIResponse(responseCode = "404",
                    description = "Employees have not been returned successfully."
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal server error."
            )
    })
    @GET
    @Path("/street-search/{searchString}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStreetByName(@PathParam("searchString") String searchString) {
        try {
            List<Street> streets = orchestrationBean.findStreetWithName(searchString);
            if (streets != null) {
                return Response.ok(streets).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Statistics.

    @Operation(description = "Get statistics for the whole organisation.", summary = "Get overall statistics")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Statistics for the entire organisation."
            ),
            @APIResponse(responseCode = "404", description = "Statistics could not be found.")
    })
    @GET
    @Path("/statistics")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStatistics() {
        try {
            return Response.ok(orchestrationBean.viewStatistics()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Get statistics for a specific branch.", summary = "Get branch statistics")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Statistics for a specific branch."
            ),
            @APIResponse(responseCode = "404", description = "Statistics could not be found.")
    })
    @GET
    @Path("/statistics/{branchId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStatisticsOfBranch(@PathParam("branchId") Integer branchId) {
        try {
            return Response.ok(orchestrationBean.viewStatisticsOfBranch(branchId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Customers.

    // Parcels.

    @Operation(description = "Get statistics for a specific branch.", summary = "Get branch statistics")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Statistics for a specific branch."
            ),
            @APIResponse(responseCode = "404", description = "Statistics could not be found.")
    })
    @GET
    @Path("/parcels")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewParcels(@QueryParam("senderId") Integer senderId,
                                @QueryParam("recipientId") Integer recipientId) {

        if (senderId != null && recipientId != null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            if (senderId != null) {
                return Response.ok(orchestrationBean.viewParcelsAsSender(senderId)).build();
            } else if (recipientId != null) {
                return Response.ok(orchestrationBean.viewParcelsAsRecipient(recipientId)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Get statistics for a specific branch.", summary = "Get branch statistics")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Statistics for a specific branch."
            ),
            @APIResponse(responseCode = "404", description = "Statistics could not be found.")
    })
    @GET
    @Path("/parcels/{parcelId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewParcels(@QueryParam("id") String id) {

        try {
            return Response.ok(orchestrationBean.viewParcel(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Messaging.

}
