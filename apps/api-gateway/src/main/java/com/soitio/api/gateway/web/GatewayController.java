package com.soitio.api.gateway.web;

import com.soitio.api.gateway.GatewayService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api")
public class GatewayController {

    private static final Logger log = LoggerFactory.getLogger(GatewayController.class);

    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }

    @GET
    @Path("{subPaths: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Object> getRoute(UriInfo uri, HttpHeaders headers) {
        return service.getRoute(uri, headers);
    }

    @POST
    @Path("{subPaths: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Object> postRoute(UriInfo uri, HttpHeaders headers, Object body) {
        log.info("Test post {}", uri.getPath());
        log.info("body {}", body);
        log.info("headers {}", headers);
        return Uni.createFrom().item(body);
    }

    @PUT
    @Path("{subPaths: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Object> putRoute(UriInfo uri, HttpHeaders headers, Object body) {
        log.info("Test {}", uri.getPath());
        return null;
    }

    @PATCH
    @Path("{subPaths: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Object> patchRoute(UriInfo uri, HttpHeaders headers, Object body) {
        log.info("Test {}", uri.getPath());
        return null;
    }

    @DELETE
    @Path("{subPaths: .*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Object> deleteRoute(UriInfo uri, HttpHeaders headers) {
        log.info("Test {}", uri.getPath());
        return null;
    }

}
