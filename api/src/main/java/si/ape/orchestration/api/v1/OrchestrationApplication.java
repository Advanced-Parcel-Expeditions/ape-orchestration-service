package si.ape.orchestration.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Orchestration API", version = "v1",
        contact = @Contact(email = "ls6727@student.uni-lj.si, js1471@student.uni-lj.si"),
        license = @License(name = "dev"), description = "API providing orchestration to the APE project. It is a " +
                                                        "facade for the other microservices."),
        servers = @Server(url = "http://localhost:8080/"))
@ApplicationPath("/v1")
public class OrchestrationApplication extends Application {
}
