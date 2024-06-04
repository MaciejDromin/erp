package pl.mlisowski.selfregister.client;

import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import pl.mlisowski.selfregister.domain.dto.RegisterDto;

@Path("/v1/agent/service")
@RegisterRestClient
public interface RegistrationClient {

    @PUT
    @Path("/register")
    void register(RegisterDto register);

    @PUT
    @Path("/deregister/{serviceId}")
    void deleteService(@PathParam("serviceId") String serviceId);

}
