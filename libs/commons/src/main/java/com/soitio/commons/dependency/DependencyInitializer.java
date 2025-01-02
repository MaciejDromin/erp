package com.soitio.commons.dependency;

import com.soitio.commons.dependency.exception.DependencyLockException;
import com.soitio.commons.dependency.model.Dependent;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DependencyInitializer {

    void createOrUpdateKey(Set<String> services, String key) throws DependencyLockException;

    void initializeDependencies();

    Map<Dependent, List<DependencyCheckService>> validateAndMakeDependencies(Set<Class<? extends Dependencies>> dependencies);

    void getCurrentAndSet(Dependent key);

    DependencyCheckMap produceDependencyMap();

}
