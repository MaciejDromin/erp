package com.soitio.api.gateway;

import com.soitio.api.gateway.client.GatewayClient;
import com.soitio.api.gateway.config.RouteDetails;
import com.soitio.api.gateway.config.GatewayConfig;
import com.soitio.api.gateway.utils.AuthUtils;
import com.soitio.auth.client.AuthService;
import com.soitio.auth.client.TokenRequest;
import com.soitio.commons.models.commons.ServiceKey;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.io.InputStream;
import java.util.function.BiFunction;

@ApplicationScoped
public class GatewayService {

    private static final int SERVICE_KEY_START_INDEX = 4;
    private final GatewayClient gatewayClient;
    private final GatewayConfig gatewayConfig;

    private final AuthService authService;

    public GatewayService(@RestClient GatewayClient gatewayClient,
                          GatewayConfig gatewayConfig,
                          @GrpcClient AuthService authService) {
        this.gatewayClient = gatewayClient;
        this.gatewayConfig = gatewayConfig;
        this.authService = authService;
    }

    public Uni<Object> getRoute(UriInfo uriInfo, HttpHeaders headers) {
        return authenticateAndExecute(uriInfo, headers, gatewayClient::getRoute);
    }

    public Uni<Object> postRoute(UriInfo uri, HttpHeaders headers, Object body) {
        return authenticateAndExecute(uri, headers, (s, m, o) -> gatewayClient.postRoute(s, m, o, body));
    }

    public Uni<Object> fileUpload(UriInfo uri, HttpHeaders headers, InputStream is) {
        return authenticateAndExecute(uri, headers,
                (s, m, o) -> gatewayClient.fileUpload(s, m, headers.getHeaderString("filename"), o, is));
    }

    public Uni<Object> putRoute(UriInfo uri, HttpHeaders headers, Object body) {
        return authenticateAndExecute(uri, headers, (s, m, o) -> gatewayClient.putRoute(s, m, o, body));
    }

    public Uni<Object> patchRoute(UriInfo uri, HttpHeaders headers, Object body) {
        return authenticateAndExecute(uri, headers, (s, m, o) -> gatewayClient.patchRoute(s, m, o, body));
    }

    public Uni<Object> deleteRoute(UriInfo uri, HttpHeaders headers, Object body) {
        return authenticateAndExecute(uri, headers, (s, m, o) -> gatewayClient.deleteRoute(s, m, o, body));
    }

    private Uni<Object> authenticateAndExecute(UriInfo uriInfo,
                                               HttpHeaders headers,
                                               TriFunction<String, MultivaluedMap<String, String>, String, Uni<Object>> httpCall) {
        String[] endpointDetails = extractPath(uriInfo.getPath());
        String token = AuthUtils.extractAuthorizationHeader(headers);
        if (token == null) return Uni.createFrom().failure(new UnauthorizedException("Missing Authorization header"));
        TokenRequest tokenRequest = TokenRequest.newBuilder()
                .setAuthToken(AuthUtils.extractToken(token))
                .build();
        return authService.validate(tokenRequest).onItem()
                .transformToUni(ar -> httpCall.apply(
                        buildRoute(gatewayConfig.routes()
                                .get(ServiceKey.getByName(endpointDetails[0])), endpointDetails[1]),
                        uriInfo.getQueryParameters(),
                        ar.getOrgId()));
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
