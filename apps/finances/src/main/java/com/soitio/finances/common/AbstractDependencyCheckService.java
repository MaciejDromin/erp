package com.soitio.finances.common;

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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDependencyCheckService<T> {

    private final ObjectMapper mapper;
    private final DependencyCheckRequester dependencyCheckRequester;

    protected AbstractDependencyCheckService(ObjectMapper mapper,
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

        deleteByIds(diff);

        return response;
    }

    public abstract void deleteByIds(Set<String> collect);

    public DependencyCheckResponse update(Dependent dependent, String id, JsonNode object) {
        var entity = findById(id);
        Object mappedDto = mapToDto(entity);
        JsonNode entityNode = mapper.valueToTree(mappedDto);
        JsonNode node = mapper.valueToTree(object);
        MergePatch patch = MergePatchUtils.fromJsonNode(node);
        MergePatch target = MergePatchUtils.fromJsonNode(entityNode);

        var response = dependencyCheckRequester.requestDependencyCheckForIds(dependent,
                Set.of(MergePatchUtils.buildContext(patch, target)),
                Action.EDIT);

        if (response.isFailed()) return response;

        updateEntity(mapToEntity(MergePatchUtils.merge(patch, target)));

        return response;
    }

    protected abstract T findById(String id);

    protected abstract T mapToEntity(MergePatch object);

    protected abstract void updateEntity(T entity);

    protected abstract Object mapToDto(T entity);

}
