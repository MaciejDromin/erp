package com.soitio.api.gateway.domain;

import jakarta.websocket.Session;

public record WsConnection(Session upstream, Session downstream) {
}
