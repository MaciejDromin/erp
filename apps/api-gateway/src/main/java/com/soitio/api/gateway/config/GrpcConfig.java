package com.soitio.api.gateway.config;

import com.soitio.auth.client.GatewayAuthClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GrpcConfig {

    @ConfigProperty(name = "auth-client.host")
    String host;

    @ConfigProperty(name = "auth-client.port")
    Integer port;

    @Produces
    @ApplicationScoped
    public GatewayAuthClient gatewayAuthClient() {
        return new GatewayAuthClient(host, port);
    }

}
