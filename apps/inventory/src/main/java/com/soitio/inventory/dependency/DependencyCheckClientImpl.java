package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.client.DependencyCheckClient;
import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DependencyCheckClientImpl implements DependencyCheckClient {

    private final Map<URI, QuarkusDependencyClient> cache;

    public DependencyCheckClientImpl() {
        this.cache = new HashMap<>();
    }

    @Override
    public DependencyCheckResponse check(URI uri, DependencyCheckRequest dependencyCheckRequest) {
        if (cache.containsKey(uri)) return cache.get(uri).check(dependencyCheckRequest);
        QuarkusDependencyClient client = QuarkusRestClientBuilder.newBuilder()
                .baseUri(uri)
                .build(QuarkusDependencyClient.class);
        cache.put(uri, client);
        return client.check(dependencyCheckRequest);
    }

}
