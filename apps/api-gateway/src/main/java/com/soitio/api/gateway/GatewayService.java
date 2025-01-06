package com.soitio.api.gateway;

import com.soitio.api.gateway.client.GatewayClient;
import com.soitio.api.gateway.config.RouteDetails;
import com.soitio.api.gateway.config.GatewayConfig;
import com.soitio.auth.client.AuthService;
import com.soitio.commons.models.commons.ServiceKey;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.io.InputStream;

@ApplicationScoped
public class GatewayService {

    private static final int SERVICE_KEY_START_INDEX = 4;
    private final GatewayClient gatewayClient;
    private final GatewayConfig gatewayConfig;

    private AuthService authService;

    public GatewayService(@RestClient GatewayClient gatewayClient,
                          GatewayConfig gatewayConfig,
                          @GrpcClient AuthService authService) {
        this.gatewayClient = gatewayClient;
        this.gatewayConfig = gatewayConfig;
        this.authService = authService;
    }

    public Uni<Object> getRoute(UriInfo uriInfo, HttpHeaders headers) {
        String[] endpointDetails = extractPath(uriInfo.getPath());
//        return authService.authenticate(null).onItem().transform(ar -> gatewayClient.getRoute(
//                buildRoute(gatewayConfig.routes()
//                        .get(ServiceKey.getByName(endpointDetails[0])), endpointDetails[1]),
//                uriInfo.getQueryParameters()));
        return gatewayClient.getRoute(
                buildRoute(gatewayConfig.routes()
                        .get(ServiceKey.getByName(endpointDetails[0])), endpointDetails[1]),
                uriInfo.getQueryParameters());
    }

    public Uni<Object> postRoute(UriInfo uri, HttpHeaders headers, Object body) {
        String[] endpointDetails = extractPath(uri.getPath());
        return gatewayClient.postRoute(
                buildRoute(gatewayConfig.routes()
                        .get(ServiceKey.getByName(endpointDetails[0])), endpointDetails[1]),
                uri.getQueryParameters(), body);
    }

    public Uni<Object> fileUpload(UriInfo uri, HttpHeaders headers, InputStream is) {
        String[] endpointDetails = extractPath(uri.getPath());
        return gatewayClient.fileUpload(
                buildRoute(gatewayConfig.routes()
                        .get(ServiceKey.getByName(endpointDetails[0])), endpointDetails[1]),
                uri.getQueryParameters(), headers.getHeaderString("filename"), is);
    }

    public Uni<Object> putRoute(UriInfo uri, HttpHeaders headers, Object body) {
        String[] endpointDetails = extractPath(uri.getPath());
        return gatewayClient.putRoute(
                buildRoute(gatewayConfig.routes()
                        .get(ServiceKey.getByName(endpointDetails[0])), endpointDetails[1]),
                uri.getQueryParameters(), body);
    }

    public Uni<Object> patchRoute(UriInfo uri, HttpHeaders headers, Object body) {
        String[] endpointDetails = extractPath(uri.getPath());
        return gatewayClient.patchRoute(
                buildRoute(gatewayConfig.routes()
                        .get(ServiceKey.getByName(endpointDetails[0])), endpointDetails[1]),
                uri.getQueryParameters(), body);
    }

    public Uni<Object> deleteRoute(UriInfo uri, HttpHeaders headers, Object body) {
        String[] endpointDetails = extractPath(uri.getPath());
        return gatewayClient.deleteRoute(
                buildRoute(gatewayConfig.routes()
                        .get(ServiceKey.getByName(endpointDetails[0])), endpointDetails[1]),
                uri.getQueryParameters(), body);
    }

    private String[] extractPath(String baseUri) {
        int secondSlashPos = baseUri.indexOf('/', SERVICE_KEY_START_INDEX + 1);
        return new String[] {baseUri.substring(SERVICE_KEY_START_INDEX + 1, secondSlashPos),
                baseUri.substring(secondSlashPos)};
    }

    private String buildRoute(RouteDetails route, String endpoint) {
        return "http://%s:%d%s".formatted(route.hostname(), route.port(), endpoint);
    }

}
