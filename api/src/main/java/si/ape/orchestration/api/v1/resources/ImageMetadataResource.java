package si.ape.orchestration.api.v1.resources;

import si.ape.orchestration.services.beans.OrchestrationBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;



@ApplicationScoped
@Path("/images")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImageMetadataResource {

    private final Logger log = Logger.getLogger(ImageMetadataResource.class.getName());

    @Inject
    private OrchestrationBean orchestrationBean;


    @Context
    protected UriInfo uriInfo;

}
