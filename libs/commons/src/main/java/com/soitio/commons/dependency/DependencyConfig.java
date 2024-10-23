package com.soitio.commons.dependency;

import dev.failsafe.Policy;

import java.util.Set;

public interface DependencyConfig {

    String getServiceName();

    Set<Class<? extends Dependencies>> getClassesWithDependencies();

    Policy<Object> getPolicy();

}
