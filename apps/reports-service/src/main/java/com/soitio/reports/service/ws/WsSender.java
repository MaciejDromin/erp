package com.soitio.reports.service.ws;

import io.quarkus.websockets.next.OpenConnections;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WsSender {

    private final OpenConnections openConnections;

    public WsSender(OpenConnections openConnections) {
        this.openConnections = openConnections;
    }

    public void send(String endpoint, Object message) {
        openConnections.findByEndpointId(endpoint).forEach(c -> c.sendTextAndAwait(message));
    }
}
