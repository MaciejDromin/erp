package com.soitio.commons.dependency;

import com.soitio.commons.dependency.model.Action;
import com.soitio.commons.dependency.model.DependencyCheckResult;

import java.util.Set;

public interface DependencyCheckService {

    default Set<DependencyCheckResult> dependencyCheck(Set<String> ids, Action action) {
        return switch (action) {
            case EDIT -> checkForEdit(ids);
            case DELETE -> checkForDelete(ids);
        };
    }

    String getServiceName();

    Set<DependencyCheckResult> checkForEdit(Set<String> ids);

    Set<DependencyCheckResult> checkForDelete(Set<String> ids);

}
