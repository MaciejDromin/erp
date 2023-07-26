package pl.mlisowski.finances.selfregister.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.mlisowski.finances.selfregister.domain.RegisterDto;
import pl.mlisowski.finances.selfregister.domain.RegisterResponseDto;

@FeignClient(name = "self-register")
public interface RegistrationClient {

    @PostMapping
    RegisterResponseDto register(RegisterDto register);

    @DeleteMapping("/{serviceId}")
    void deleteService(String serviceId);

}
