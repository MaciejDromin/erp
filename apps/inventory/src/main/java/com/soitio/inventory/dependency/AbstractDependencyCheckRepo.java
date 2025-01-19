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
import com.soitio.inventory.commons.OrgMongoRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDependencyCheckRepo<T> implements OrgMongoRepository<T> {

    private final ObjectMapper mapper;
    private final DependencyCheckRequester dependencyCheckRequester;

    protected AbstractDependencyCheckRepo(ObjectMapper mapper,
                                          DependencyCheckRequester dependencyCheckRequester) {
        this.mapper = mapper;
        this.dependencyCheckRequester = dependencyCheckRequester;
    }

    public DependencyCheckResponse delete(Dependent dependent, Set<String> ids, String orgId) {
        var response = dependencyCheckRequester.requestDependencyCheckForIds(dependent, ids.stream()
                .map(DependencyCheckContext::emptyOfId)
                .collect(Collectors.toSet()), Action.DELETE);

        Set<String> diff = new HashSet<>(ids);
        response.getResults()
                .stream()
                .map(DependencyCheckResult::getId)
                .toList()
                .forEach(diff::remove);

        deleteByIdsAndOrgId(diff, orgId);

        return response;
    }

    public DependencyCheckResponse update(Dependent dependent, String id, JsonNode node, String orgId) {
        T entity = findByIdAndOrgId(id, orgId);
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
