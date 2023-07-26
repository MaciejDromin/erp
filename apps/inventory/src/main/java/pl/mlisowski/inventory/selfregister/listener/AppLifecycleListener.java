package pl.mlisowski.inventory.selfregister.listener;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import pl.mlisowski.inventory.selfregister.client.RegistrationClient;
import pl.mlisowski.inventory.selfregister.domain.RegisterDto;

@Slf4j
@ApplicationScoped
public class AppLifecycleListener {

    @ConfigProperty(name = "service-discovery.host")
    private String host;
    @ConfigProperty(name = "service-discovery.port")
    private Integer port;
    @ConfigProperty(name = "service-discovery.service-name")
    private String serviceName;

    @RestClient
    private RegistrationClient registrationClient;
    private String serviceId;

    void onStart(@Observes StartupEvent event) {
        this.serviceId = registrationClient.register(RegisterDto.builder()
                .serviceName(this.serviceName)
                .address(this.host)
                .port(this.port)
                .build()).getServiceId();
    }

    void onStop(@Observes ShutdownEvent event) {
        try (Response res = registrationClient.deleteService(this.serviceId)){}
    }

}
