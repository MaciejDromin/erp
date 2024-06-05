package pl.mlisowski.selfregister.listener;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import pl.mlisowski.selfregister.client.RegistrationClient;
import pl.mlisowski.selfregister.domain.ServiceContext;
import pl.mlisowski.selfregister.domain.dto.CheckDto;
import pl.mlisowski.selfregister.domain.dto.RegisterDto;
import pl.mlisowski.selfregister.domain.dto.WeightsDto;

@Slf4j
@ApplicationScoped
public class AppLifecycleListener {

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
