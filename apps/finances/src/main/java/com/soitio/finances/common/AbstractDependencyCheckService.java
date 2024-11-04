package com.soitio.finances.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.model.Action;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.utils.MergePatchUtils;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDependencyCheckService<T> {

    private final ObjectMapper mapper;
    private final DependencyCheckRequester dependencyCheckRequester;

    protected AbstractDependencyCheckService(ObjectMapper mapper,
                                             DependencyCheckRequester dependencyCheckRequester) {
        this.mapper = mapper;
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

    public <T> DependencyCheckResponse update(Dependent dependent, String id, JsonNode object) {
        var response = dependencyCheckRequester.requestDependencyCheckForIds(dependent, Set.of(id), Action.EDIT);

        if (response.isFailed()) return response;

        updateOne(id, object);

        return response;
    }

    public void updateOne(String id, JsonNode node) {
        var entity = findById(id);
        JsonNode entityNode = mapper.valueToTree(entity);
        MergePatch patch = MergePatchUtils.fromJsonNode(node);
        MergePatch target = MergePatchUtils.fromJsonNode(entityNode);
        MergePatch merged = MergePatchUtils.merge(patch, target);
        updateEntity(mapToEntity(merged));
    }

    protected abstract T findById(String id);

    protected abstract T mapToEntity(MergePatch object);

    protected abstract void updateEntity(T entity);

}
