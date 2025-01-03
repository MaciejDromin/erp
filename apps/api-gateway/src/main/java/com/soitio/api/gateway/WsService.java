package com.soitio.api.gateway;

import com.soitio.api.gateway.client.WsClient;
import com.soitio.api.gateway.config.GatewayConfig;
import com.soitio.api.gateway.config.RouteDetails;
import com.soitio.api.gateway.domain.WsConnection;
import com.soitio.commons.models.commons.ServiceKey;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Session;
import java.net.URI;

@ApplicationScoped
public class WsService {

    private static final int PATH_END_INDEX = 5;
    private final GatewayConfig gatewayConfig;
    private final WsContext context;

    public WsService(GatewayConfig gatewayConfig,
                     WsContext context) {
        this.gatewayConfig = gatewayConfig;
        this.context = context;
    }

    public void createConnection(Session upstream, String service, String query) {
        String endpointDetails = extractPath(query);
        try {
            Session session = ContainerProvider.getWebSocketContainer().connectToServer(WsClient.class,
                    new URI(buildRoute(gatewayConfig.routes().get(ServiceKey.getByName(service)), endpointDetails)));
            context.add(service, new WsConnection(upstream, session));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private String extractPath(String query) {
        return query.substring(PATH_END_INDEX);
    }

    private String buildRoute(RouteDetails route, String endpoint) {
        return "ws://%s:%d%s".formatted(route.hostname(), route.port(), endpoint.replaceAll("%2F", "/"));
    }

    public Session getUpstreamSession(Session session) {
        return context.findUpstreamByDownStream(session);
    }

    public void removeConnection(String service) {
        context.remove(service);
    }

    public void sendDownstream(String service, String message) {
        WsConnection connection = context.getByKey(service);
        connection.downstream().getAsyncRemote().sendText(message);
    }
}
