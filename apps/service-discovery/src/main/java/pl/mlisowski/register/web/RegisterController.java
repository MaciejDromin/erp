package pl.mlisowski.register.web;

import io.vertx.ext.consul.Service;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.mlisowski.register.application.RegistrationService;
import pl.mlisowski.register.domain.dto.RegisterDto;
import pl.mlisowski.register.domain.dto.RegisterResponseDto;

import java.util.List;

@Path("/register")
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final RegistrationService registrationService;

    @GET
    public List<Service> listAllServices() {
        return registrationService.getAllServices();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public RegisterResponseDto registerService(RegisterDto register) {
        return registrationService.registerService(register);
    }

    @DELETE
    @Path("/{serviceId}")
    public Response deleteService(String serviceId) {
        registrationService.deleteService(serviceId);
        return Response.noContent().build();
    }

}
