package com.soitio.reports.service.ws;

import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.inject.Inject;

@WebSocket(path = "/reports")
public class WebsocketServer {

    @Inject
    WebSocketConnection connection;

    @OnOpen(broadcast = true)
    public String onOpen() {
        return "Connected";
    }

    @OnClose
    public void onClose() {
        connection.broadcast().sendTextAndAwait("Disconnected");
    }

}
