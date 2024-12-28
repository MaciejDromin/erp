package com.soitio.api.gateway.web;

import com.soitio.api.gateway.ConfigStoreService;
import com.soitio.api.gateway.domain.ConfigResourceDto;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/store")
public class ConfigStoreController {

    private final ConfigStoreService service;

    public ConfigStoreController(ConfigStoreService service) {
        this.service = service;
    }

    @PUT
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Boolean> updateOrCreateKey(@PathParam("key") String key, Object value) {
        return service.updateOrCreate(key, value);
    }

    @GET
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<ConfigResourceDto> getValue(@PathParam("key") String key) {
        return service.getCurrentVal(key);
    }

}
