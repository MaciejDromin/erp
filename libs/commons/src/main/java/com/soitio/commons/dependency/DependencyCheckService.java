package com.soitio.commons.dependency;

import com.soitio.commons.dependency.model.DependencyCheckResult;

import java.util.Set;

public interface DependencyCheckService {

    Set<DependencyCheckResult> dependencyCheck(Set<String> ids);

    String getServiceName();

}
