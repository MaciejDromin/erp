package com.soitio.inventory.dependency;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.model.Action;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.utils.MergePatchUtils;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.bson.types.ObjectId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDependencyCheckRepo<T> implements PanacheMongoRepository<T> {

    private final ObjectMapper mapper;
    private final DependencyCheckRequester dependencyCheckRequester;

    protected AbstractDependencyCheckRepo(ObjectMapper mapper,
                                          DependencyCheckRequester dependencyCheckRequester) {
        this.mapper = mapper;
        this.dependencyCheckRequester = dependencyCheckRequester;
    }

    public DependencyCheckResponse delete(Dependent dependent, Set<String> ids) {
        var response = dependencyCheckRequester.requestDependencyCheckForIds(dependent, ids.stream()
                .map(DependencyCheckContext::emptyOfId)
                .collect(Collectors.toSet()), Action.DELETE);

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

    public DependencyCheckResponse update(Dependent dependent, String id, JsonNode node) {
        var entity = findById(new ObjectId(id));
        JsonNode entityNode = mapper.valueToTree(entity);
        MergePatch patch = MergePatchUtils.fromJsonNode(node);
        MergePatch target = MergePatchUtils.fromJsonNode(entityNode);

        var response = dependencyCheckRequester.requestDependencyCheckForIds(dependent,
                Set.of(MergePatchUtils.buildContext(patch, target)),
                Action.EDIT);

        if (response.isFailed()) return response;

        update(mapToEntity(MergePatchUtils.merge(patch, target)));

        return response;
    }

    protected abstract T mapToEntity(MergePatch object);

}
