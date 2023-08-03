package pl.mlisowski.selfregister.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/healthcheck")
public class HealthcheckController {

    @GET
    public Response healthcheck() {
        return Response.ok().build();
    }

}
