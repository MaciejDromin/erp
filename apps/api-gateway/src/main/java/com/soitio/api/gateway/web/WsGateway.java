package com.soitio.api.gateway.web;

import com.soitio.api.gateway.WsService;
import com.soitio.api.gateway.domain.WsConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{service}")
@ApplicationScoped
public class WsGateway {

    private static final Logger log = LoggerFactory.getLogger(WsGateway.class);

    private final WsService wsService;

    public WsGateway(WsService wsService) {
        this.wsService = wsService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("service") String service) {
        wsService.createConnection(session, service, session.getQueryString());
    }

    @OnClose
    public void onClose(Session session, @PathParam("service") String service) {
        wsService.removeConnection(service);
    }

    @OnError
    public void onError(Session session, @PathParam("service") String service, Throwable throwable) {
        wsService.removeConnection(service);
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("service") String service) {
        wsService.sendDownstream(service, message);
    }

}
