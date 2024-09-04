package com.soitio.selfregister.self.register.quarkus.runtime.listener;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.util.UUID;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.soitio.selfregister.self.register.quarkus.runtime.client.RegistrationClient;
import com.soitio.selfregister.self.register.quarkus.runtime.domain.ServiceContext;
import com.soitio.selfregister.self.register.quarkus.runtime.domain.dto.CheckDto;
import com.soitio.selfregister.self.register.quarkus.runtime.domain.dto.RegisterDto;
import com.soitio.selfregister.self.register.quarkus.runtime.domain.dto.WeightsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AppLifecycleListener {

    private static final Logger log = LoggerFactory.getLogger(AppLifecycleListener.class);

    @ConfigProperty(name = "service-discovery.host")
    String host;
    @ConfigProperty(name = "service-discovery.port")
    Integer port;
    @ConfigProperty(name = "service-discovery.service-name")
    String serviceName;

    @RestClient
    RegistrationClient registrationClient;
    private final ServiceContext serviceContext;

    public AppLifecycleListener(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    void onStart(@Observes StartupEvent event) {
        log.info("Registering application of name: {}", serviceName);
        serviceContext.setServiceId(UUID.randomUUID().toString());
        registrationClient.register(RegisterDto.builder()
                .id(serviceContext.getServiceId())
                .name(this.serviceName)
                .address(this.host)
                .port(this.port)
                .check(CheckDto.builder()
                        .http("http://%s:%d/healthcheck".formatted(this.host, this.port))
                        .build())
                .weights(new WeightsDto(10, 3))
                .build());
    }

    void onStop(@Observes ShutdownEvent event) {
        log.info("De-registering application of name: {}", serviceName);
        registrationClient.deleteService(serviceContext.getServiceId());
    }

}
