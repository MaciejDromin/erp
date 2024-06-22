package com.soitio.dashboard;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/dashboard")
public class DebugController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String debug() {
        return "Hello from dashboard!";
    }

}
