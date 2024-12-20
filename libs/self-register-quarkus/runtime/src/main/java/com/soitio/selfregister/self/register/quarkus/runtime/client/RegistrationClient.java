package com.soitio.selfregister.self.register.quarkus.runtime.client;

import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import com.soitio.selfregister.self.register.quarkus.runtime.domain.dto.RegisterDto;

@Path("/v1/agent/service")
@RegisterRestClient(configKey = "consul-api")
public interface RegistrationClient {

    @PUT
    @Path("/register")
    void register(RegisterDto register);

    @PUT
    @Path("/deregister/{serviceId}")
    void deleteService(@PathParam("serviceId") String serviceId);

}
