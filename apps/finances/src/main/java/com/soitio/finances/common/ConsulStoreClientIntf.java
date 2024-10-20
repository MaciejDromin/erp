package com.soitio.finances.common;

import com.soitio.commons.dependency.client.ConsulStoreClient;
import com.soitio.commons.dependency.model.KeyValue;
import com.soitio.commons.dependency.model.Session;
import com.soitio.finances.common.configuration.FeignConfig;
import java.util.List;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "consul-store",
             url = "${spring.cloud.openfeign.client.config.self-register.url}",
             configuration = FeignConfig.class)
public interface ConsulStoreClientIntf extends ConsulStoreClient {

    @Override
    @PutMapping("/v1/kv/{key}")
    boolean updateKey(Set<String> body,
                      @PathVariable("key") String key,
                      @RequestParam("acquire") String acquire);

    @Override
    @PutMapping("/v1/kv/{key}")
    void relaeseKey(Set<String> body,
                    @PathVariable("key") String key,
                    @RequestParam("release") String release);

    @Override
    @GetMapping("/v1/kv/{key}")
    List<KeyValue> getCurrentValue(@PathVariable("key") String s);

    @Override
    @PutMapping("/v1/session/create")
    Session createSession();
}
