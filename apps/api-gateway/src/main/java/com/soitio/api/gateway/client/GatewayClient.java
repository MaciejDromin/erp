package com.soitio.api.gateway.client;

import io.quarkus.rest.client.reactive.Url;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/")
@RegisterRestClient(baseUri = "localhost:8080")
public interface GatewayClient {

    @GET
    Uni<Object> getRoute(@Url String url, @RestQuery MultivaluedMap<String, String> queryParams);

}
