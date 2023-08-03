package pl.mlisowski.selfregister.listener;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import pl.mlisowski.selfregister.client.RegistrationClient;
import pl.mlisowski.selfregister.domain.RegisterDto;

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
    private String serviceId;

    void onStart(@Observes StartupEvent event) {
        log.info("Registering application of name: {}", serviceName);
        this.serviceId = registrationClient.register(RegisterDto.builder()
                .serviceName(this.serviceName)
                .address(this.host)
                .port(this.port)
                .build()).getServiceId();
    }

    void onStop(@Observes ShutdownEvent event) {
        log.info("De-registering application of name: {}", serviceName);
        try (Response res = registrationClient.deleteService(this.serviceId)){}
    }

}
