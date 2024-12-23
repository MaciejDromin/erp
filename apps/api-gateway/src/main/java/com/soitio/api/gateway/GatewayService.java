package com.soitio.api.gateway;

import com.soitio.api.gateway.application.PathResourceRepository;
import com.soitio.api.gateway.client.GatewayClient;
import com.soitio.api.gateway.domain.PathResource;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class GatewayService {

    private static final int ENDPOINT_START_INDEX = 4;
    private final GatewayClient gatewayClient;
    private final PathResourceRepository pathResourceRepository;

    public GatewayService(@RestClient GatewayClient gatewayClient,
                          PathResourceRepository pathResourceRepository) {
        this.gatewayClient = gatewayClient;
        this.pathResourceRepository = pathResourceRepository;
    }

    public Uni<Object> getRoute(UriInfo uriInfo, HttpHeaders headers) {
        // auth token from headers
        // buildURL
        String endpoint = extractPath(uriInfo.getPath());
        return pathResourceRepository.getByPath(endpoint)
                .onItem()
                .transform(pr -> buildRoute(pr, endpoint))
                .onItem()
                .transformToUni(p -> gatewayClient.getRoute(p, uriInfo.getQueryParameters()))
                .onFailure()
                .transform(NotFoundException::new);
    }

    private String extractPath(String baseUri) {
        return baseUri.substring(ENDPOINT_START_INDEX);
    }

    private String buildRoute(PathResource resource, String endpoint) {
        return "http://%s:%d%s".formatted(resource.getHostname(), resource.getPort(), endpoint);
    }

    private Uni<Object> attemptRequest(String uri, Throwable t, MultivaluedMap<String, String> queryParams) {
        if (t != null) {
            throw new RuntimeException("Sorry but we couldn't find the path you were looking for!");
        }
        return gatewayClient.getRoute(uri, queryParams);
    }

}
