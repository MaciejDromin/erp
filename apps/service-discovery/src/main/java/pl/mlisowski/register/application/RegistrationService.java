package pl.mlisowski.register.application;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.Service;
import io.vertx.ext.consul.ServiceOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.consul.ConsulClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.mlisowski.register.domain.dto.RegisterDto;
import pl.mlisowski.register.domain.dto.RegisterResponseDto;

import java.util.List;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class RegistrationService {

    @ConfigProperty(name = "consul.host") String host;
    @ConfigProperty(name = "consul.port") int port;

    private ConsulClient consulClient;

    public void init(@Observes StartupEvent ev, Vertx vertx) {
        consulClient = ConsulClient.create(vertx, new ConsulClientOptions().setHost(host).setPort(port));
    }

    public RegisterResponseDto registerService(RegisterDto register) {
        String uuid = UUID.randomUUID().toString();
        consulClient.registerServiceAndAwait(new ServiceOptions()
                .setPort(register.getPort())
                .setAddress(register.getAddress())
                .setName(register.getServiceName())
                .setCheckOptions(new CheckOptions()
                        .setInterval("5s")
                        .setHttp("http://%s:%s/%s".formatted(register.getAddress(),
                                register.getPort(), "healthcheck")))
                .setId(uuid)
        );
//        consulClient.healthChecksAndAwait()
        return new RegisterResponseDto(uuid);
    }

    public List<Service> getAllServices() {
        return consulClient.catalogServices()
                .await()
                .indefinitely()
                .getList()
                .stream()
                .filter(service -> !service.getName().equalsIgnoreCase("consul"))
                .toList();
    }

    public void deleteService(String serviceId) {
        consulClient.deregisterServiceAndAwait(serviceId);
    }
}
