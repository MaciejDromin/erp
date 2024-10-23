package com.soitio.finances.common;

import com.soitio.commons.dependency.client.DependencyCheckClient;
import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DependencyCheckClient")
public interface DependencyCheckClientIntf extends DependencyCheckClient {

    @Override
    @PostMapping("/dependencies")
    DependencyCheckResponse check(URI uri, @RequestBody DependencyCheckRequest dependencyCheckRequest);
}
