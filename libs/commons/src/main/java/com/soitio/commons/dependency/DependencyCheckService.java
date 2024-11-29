package com.soitio.commons.dependency;

import com.soitio.commons.dependency.model.Action;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckResult;

import java.util.Set;

public interface DependencyCheckService {

    default Set<DependencyCheckResult> dependencyCheck(Set<DependencyCheckContext> context, Action action) {
        return switch (action) {
            case EDIT -> checkForEdit(context);
            case DELETE -> checkForDelete(context);
        };
    }

    String getServiceName();

    Set<DependencyCheckResult> checkForEdit(Set<DependencyCheckContext> context);

    Set<DependencyCheckResult> checkForDelete(Set<DependencyCheckContext> context);

}
