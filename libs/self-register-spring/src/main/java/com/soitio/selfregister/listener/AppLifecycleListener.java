package com.soitio.selfregister.listener;

import com.soitio.selfregister.client.RegistrationClient;
import com.soitio.selfregister.domain.ServiceContext;
import com.soitio.selfregister.domain.dto.CheckDto;
import com.soitio.selfregister.domain.dto.RegisterDto;
import com.soitio.selfregister.domain.dto.WeightsDto;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class AppLifecycleListener {

    @Value("${server.address:finances}")
    private String address;
    @Value("${server.port}")
    private Integer port;
    @Value("${service-discovery.service-name}")
    private String serviceName;

    private final RegistrationClient registrationClient;
    private final ServiceContext serviceContext;

    public AppLifecycleListener(RegistrationClient registrationClient, ServiceContext serviceContext) {
        this.registrationClient = registrationClient;
        this.serviceContext = serviceContext;
    }

    @PostConstruct
    public void init() {
        log.info("Registering application of name: {}", serviceName);
        serviceContext.setServiceId(UUID.randomUUID().toString());
        registrationClient.register(RegisterDto.builder()
                .id(serviceContext.getServiceId())
                .name(this.serviceName)
                .address(this.address)
                .port(this.port)
                .check(CheckDto.builder()
                        .http("http://%s:%d/healthcheck".formatted(this.address, this.port))
                        .build())
                .weights(new WeightsDto(10, 3))
                .build());
    }

    @PreDestroy
    public void destroy() {
        log.info("De-registering application of name: {}", serviceName);
        registrationClient.deleteService(serviceContext.getServiceId());
    }

}
