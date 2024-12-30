package com.soitio.finances.common;

import com.soitio.commons.dependency.client.DependencyKeyClient;
import com.soitio.commons.dependency.model.StoreKey;
import com.soitio.finances.common.configuration.FeignConfig;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "consul-store",
             url = "${spring.cloud.openfeign.client.config.gateway.url}",
             configuration = FeignConfig.class)
public interface DependencyKeyClientIntf extends DependencyKeyClient {

    @Override
    @PutMapping("/store/{key}")
    boolean updateKey(@PathVariable("key") String key,
                      Set<String> body);


    @Override
    @GetMapping("/store/{key}")
    StoreKey getCurrentValue(@PathVariable("key") String s);

}
