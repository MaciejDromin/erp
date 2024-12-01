package com.soitio.commons.dependency.model;

import java.util.Set;

public record DependencyCheckContext(String id, Set<DependencyCheckDiff> diff) {

    public static DependencyCheckContext emptyOfId(String id) {
        return new DependencyCheckContext(id, Set.of());
    }

}
