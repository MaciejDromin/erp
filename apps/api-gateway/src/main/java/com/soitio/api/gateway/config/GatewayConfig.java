package com.soitio.api.gateway.config;

import com.soitio.commons.models.commons.ServiceKey;
import io.smallrye.config.ConfigMapping;
import java.util.Map;

@ConfigMapping(prefix = "gateway")
public interface GatewayConfig {

    Map<ServiceKey, RouteDetails> routes();

}
