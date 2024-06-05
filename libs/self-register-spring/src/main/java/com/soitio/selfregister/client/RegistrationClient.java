package com.soitio.selfregister.client;

import com.soitio.selfregister.domain.dto.RegisterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "self-register")
public interface RegistrationClient {

    @PutMapping("/v1/agent/service/register")
    void register(RegisterDto register);

    @PutMapping("/v1/agent/service/deregister/{serviceId}")
    void deleteService(@PathVariable("serviceId") String serviceId);

}
