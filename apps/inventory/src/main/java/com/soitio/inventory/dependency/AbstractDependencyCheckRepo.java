package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.model.Action;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.dependency.model.Dependent;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.bson.types.ObjectId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDependencyCheckRepo<T> implements PanacheMongoRepository<T> {

    private final DependencyCheckRequester dependencyCheckRequester;

    protected AbstractDependencyCheckRepo(DependencyCheckRequester dependencyCheckRequester) {
        this.dependencyCheckRequester = dependencyCheckRequester;
    }

    protected AbstractDependencyCheckRepo() {
        this(null);
    }

    public DependencyCheckResponse delete(Dependent dependent, Set<String> ids) {
        var response = dependencyCheckRequester.requestDependencyCheckForIds(dependent, ids, Action.DELETE);

        Set<String> diff = new HashSet<>(ids);
        response.getResults()
                .stream()
                .map(DependencyCheckResult::getId)
                .toList()
                .forEach(diff::remove);

        deleteByIds(diff.stream()
                .map(ObjectId::new)
                .collect(Collectors.toSet()));

        return response;
    }

    private void deleteByIds(Set<ObjectId> diff) {
        delete("_id in ?1", diff);
    }

}
