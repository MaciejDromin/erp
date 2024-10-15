package com.soitio.commons.dependency.web;

import com.soitio.commons.dependency.DependencyCheckMap;
import com.soitio.commons.dependency.DependencyUtils;
import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;


public abstract class DependencyCheckControllerBaseImpl implements DependencyCheckController {

    private final DependencyCheckMap dependencyCheckMap;

    protected DependencyCheckControllerBaseImpl(DependencyCheckMap dependencyCheckMap) {
        this.dependencyCheckMap = dependencyCheckMap;
    }

    protected DependencyCheckResponse handle(DependencyCheckRequest checkRequest) {
        return buildResponse(dependencyCheckMap.getServicesForDependent(checkRequest.getDependent()).stream()
                .map(service -> service.dependencyCheck(checkRequest.getIds(), checkRequest.getAction()))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(DependencyCheckResult::getId,
                        r -> r,
                        DependencyUtils::mergeResults)).values());
    }

    private DependencyCheckResponse buildResponse(Collection<DependencyCheckResult> resultMap) {
        return new DependencyCheckResponse(new HashSet<>(resultMap), resultMap.stream()
                .map(DependencyCheckResult::isFailed)
                .filter(Boolean::booleanValue)
                .findFirst()
                .orElse(false));
    }

}
