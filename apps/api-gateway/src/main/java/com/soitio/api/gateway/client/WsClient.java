package com.soitio.api.gateway.client;

import com.soitio.api.gateway.WsService;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;

@ClientEndpoint
public class WsClient {

    private final WsService service;
    private Session upstream;

    public WsClient(WsService service) {
        this.service = service;
    }

    @OnMessage
    public void message(Session session, String msg) {
        if (this.upstream == null) {
            this.upstream = service.getUpstreamSession(session);
        }
        upstream.getAsyncRemote().sendText(msg);
    }

}
