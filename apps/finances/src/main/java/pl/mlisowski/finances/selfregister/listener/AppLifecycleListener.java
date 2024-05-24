package pl.mlisowski.finances.selfregister.listener;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.mlisowski.finances.selfregister.client.RegistrationClient;
import pl.mlisowski.finances.selfregister.domain.RegisterDto;

@Slf4j
//@Component
public class AppLifecycleListener {

    @Value("${server.address:finances}")
    private String address;
    @Value("${server.port}")
    private Integer port;
    @Value("${service-discovery.service-name}")
    private String serviceName;
    private String serviceId;

    private final RegistrationClient registrationClient;

    public AppLifecycleListener(RegistrationClient registrationClient) {
        this.registrationClient = registrationClient;
    }

    @PostConstruct
    public void init() {
        this.serviceId = registrationClient.register(RegisterDto.builder()
                .serviceName(this.serviceName)
                .address(this.address)
                .port(this.port)
                .build()).getServiceId();
    }

    @PreDestroy
    public void destroy() {
        log.info("Im shutting down {}", serviceId);
        registrationClient.deleteService(this.serviceId);
    }

}
