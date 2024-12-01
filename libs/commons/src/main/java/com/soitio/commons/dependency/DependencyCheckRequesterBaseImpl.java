package com.soitio.commons.dependency;

import com.soitio.commons.dependency.client.ConsulStoreClient;
import com.soitio.commons.dependency.client.DependencyCheckClient;
import com.soitio.commons.dependency.model.Action;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.dependency.model.Dependent;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DependencyCheckRequesterBaseImpl implements DependencyCheckRequester {

    private final ConsulStoreClient consulStoreClient;
    private final DependencyCheckClient dependencyCheckClient;

    protected DependencyCheckRequesterBaseImpl(ConsulStoreClient consulStoreClient,
                                               DependencyCheckClient dependencyCheckClient) {
        this.consulStoreClient = consulStoreClient;
        this.dependencyCheckClient = dependencyCheckClient;
    }

    @Override
    public DependencyCheckResponse requestDependencyCheckForIds(Dependent dependent,
                                                                Set<DependencyCheckContext> context,
                                                                Action action) {
        final DependencyCheckRequest request = new DependencyCheckRequest(dependent, action, context);
        Set<String> services = getServices(dependent);
        return services.parallelStream()
                .map(host -> dependencyCheckClient.check(URI.create(host), request))
                .reduce(new DependencyCheckResponse(), this::mergeFunction);
    }

    private DependencyCheckResponse mergeFunction(DependencyCheckResponse o,
                                                  DependencyCheckResponse n) {
        if (!o.isFailed() && !n.isFailed()) return n;
        var mergedResults = Stream.concat(o.getResults().stream(), n.getResults().stream())
                .collect(Collectors.toMap(DependencyCheckResult::getId,
                        r -> r,
                        DependencyUtils::mergeResults)).values();
        return new DependencyCheckResponse(new HashSet<>(mergedResults), true);
    }

    private Set<String> getServices(Dependent dependent) {
        try {
            return DependencyUtils.decodeBase64(consulStoreClient.getCurrentValue(dependent.getName())
                    .getFirst().getValue());
        } catch (Exception e) {
            // no key found
        }
        return Set.of();
    }
}
