package com.soitio.reports.service.ws;

import com.soitio.commons.ws.WsMessage;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.inject.Inject;

@WebSocket(path = "/test")
public class WebsocketServer {


    public enum MessageType {USER_JOINED, USER_LEFT, CHAT_MESSAGE}
    public record ChatMessage(MessageType type, String message) {
    }

    @Inject
    WebSocketConnection connection;

    @OnOpen(broadcast = true)
    public ChatMessage onOpen() {
        return new ChatMessage(MessageType.USER_JOINED, "test from server");
    }

    @OnClose
    public void onClose() {
        ChatMessage departure = new ChatMessage(MessageType.USER_LEFT, null);
        connection.broadcast().sendTextAndAwait(departure);
    }

    @OnTextMessage(broadcast = true)
    public ChatMessage onMessage(ChatMessage message) {
        return message;
    }

    public void send(WsMessage<?> event) {
        connection.broadcast().sendTextAndAwait(event);
    }

}
