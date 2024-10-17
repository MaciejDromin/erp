package com.soitio.commons.dependency.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class DependencyCheckResponse {

    private Set<DependencyCheckResult> results;
    private boolean failed;

    public DependencyCheckResponse() {
        this.results = Set.of();
        this.failed = false;
    }
}
