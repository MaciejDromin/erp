package com.soitio.finances.common;

import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.model.Action;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.dependency.model.Dependent;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDependencyCheckService {

    private final DependencyCheckRequester dependencyCheckRequester;

    protected AbstractDependencyCheckService(DependencyCheckRequester dependencyCheckRequester) {
        this.dependencyCheckRequester = dependencyCheckRequester;
    }

    public DependencyCheckResponse delete(Dependent dependent, Set<String> ids) {
        var response = dependencyCheckRequester.requestDependencyCheckForIds(dependent, ids, Action.DELETE);

        Set<String> diff = new HashSet<>(ids);

        response.getResults()
                .stream()
                .map(DependencyCheckResult::getId)
                .toList()
                .forEach(diff::remove);

        deleteByIds(diff);

        return response;
    }

    public abstract void deleteByIds(Set<String> collect);

}
