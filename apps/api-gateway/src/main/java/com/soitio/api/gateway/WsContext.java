package com.soitio.api.gateway;

import com.soitio.api.gateway.domain.WsConnection;
import jakarta.inject.Singleton;
import jakarta.websocket.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class WsContext {

    private final Map<String, WsConnection> sessions = new ConcurrentHashMap<>();

    public Map<String, WsConnection> getSessions() {
        return sessions;
    }

    public void add(String key, WsConnection connection) {
        sessions.put(key, connection);
    }

    public void remove(String key) {
        sessions.remove(key);
    }

    public Session findUpstreamByDownStream(Session session) {
        return sessions.values().stream()
                .filter(wc -> wc.downstream() == session)
                .findAny()
                .map(WsConnection::upstream)
                .orElseThrow();
    }

    public WsConnection getByKey(String service) {
        return sessions.get(service);
    }
}
