package pl.mlisowski.selfregister.client;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import pl.mlisowski.selfregister.domain.RegisterDto;
import pl.mlisowski.selfregister.domain.RegisterResponseDto;

@Path("/register")
@RegisterRestClient
public interface RegistrationClient {

    @POST
    RegisterResponseDto register(RegisterDto register);

    @DELETE
    @Path("/{serviceId}")
    Response deleteService(String serviceId);

}
