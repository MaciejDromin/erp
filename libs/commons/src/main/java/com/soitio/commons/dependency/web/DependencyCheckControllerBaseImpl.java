package com.soitio.commons.dependency.web;

import com.soitio.commons.dependency.DependencyCheckMap;
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
                .map(service -> service.dependencyCheck(checkRequest.getIds()))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(DependencyCheckResult::getId, r -> r, this::mergeFunction)).values());
    }

    private DependencyCheckResult mergeFunction(DependencyCheckResult o, DependencyCheckResult n) {
        if (!o.isFailed() && !n.isFailed()) return n;
        return new DependencyCheckResult(o.getId(), true, o.getReason() + ", " + n.getReason());
    }

    private DependencyCheckResponse buildResponse(Collection<DependencyCheckResult> resultMap) {
        return new DependencyCheckResponse(new HashSet<>(resultMap), resultMap.stream()
                .map(DependencyCheckResult::isFailed)
                .filter(Boolean::booleanValue)
                .findFirst()
                .orElse(false));
    }

}
