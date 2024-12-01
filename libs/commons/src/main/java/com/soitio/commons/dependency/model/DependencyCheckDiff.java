package com.soitio.commons.dependency.model;

import java.util.Objects;

public record DependencyCheckDiff(String field, String oldValue, String newValue) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyCheckDiff that = (DependencyCheckDiff) o;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }
}
