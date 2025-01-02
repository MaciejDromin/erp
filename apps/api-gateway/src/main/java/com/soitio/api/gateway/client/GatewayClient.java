package com.soitio.api.gateway.client;

import io.quarkus.rest.client.reactive.Url;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;
import java.io.InputStream;

@Path("/")
@RegisterRestClient(baseUri = "localhost:8080")
public interface GatewayClient {

    @GET
    Uni<Object> getRoute(@Url String url, @RestQuery MultivaluedMap<String, String> queryParams);

    @POST
    Uni<Object> postRoute(@Url String p, @RestQuery MultivaluedMap<String, String> queryParameters, Object body);

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Object> fileUpload(@Url String p,
                           @RestQuery MultivaluedMap<String, String> queryParameters,
                           @HeaderParam("filename") String filename,
                           InputStream is);

    @PUT
    Uni<Object> putRoute(@Url String p, @RestQuery MultivaluedMap<String, String> queryParameters, Object body);

    @PATCH
    Uni<Object> patchRoute(@Url String p, @RestQuery MultivaluedMap<String, String> queryParameters, Object body);

    @DELETE
    Uni<Object> deleteRoute(@Url String p, @RestQuery MultivaluedMap<String, String> queryParameters, Object body);

}
