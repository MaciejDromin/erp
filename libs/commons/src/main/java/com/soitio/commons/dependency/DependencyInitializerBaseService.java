package com.soitio.commons.dependency;

import com.soitio.commons.dependency.client.ConsulStoreClient;
import com.soitio.commons.dependency.exception.DependencyLockException;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.commons.dependency.model.Session;
import dev.failsafe.Failsafe;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DependencyInitializerBaseService implements DependencyInitializer {

    private final ConsulStoreClient consulStoreClient;
    private final DependencyConfig dependencyConfig;
    private final Map<String, DependencyCheckService> checkServiceMapping;
    protected DependencyCheckMap dependencyCheckMap;

    protected DependencyInitializerBaseService(ConsulStoreClient consulStoreClient,
                                               DependencyConfig dependencyConfig,
                                               List<DependencyCheckService> dependencyCheckServices) {
        this.consulStoreClient = consulStoreClient;
        this.dependencyConfig = dependencyConfig;
        this.checkServiceMapping = buildMapping(dependencyCheckServices);
    }

    protected DependencyInitializerBaseService() {
        this(null, null, null);
    }

    @Override
    public void createOrUpdateKey(Set<String> services, String key, Session session) throws DependencyLockException {
        boolean ret = consulStoreClient.updateKey(services, key, session.getId());
        if (!ret) {
            throw new DependencyLockException("Key is locked by another client");
        }
        consulStoreClient.relaeseKey(services, key, session.getId());
    }

    @Override
    public void initializeDependencies() {
        Set<Class<? extends Dependencies>> dependencies = dependencyConfig.getClassesWithDependencies();
        var dependenciesMap = validateAndMakeDependencies(dependencies);
        this.dependencyCheckMap = new DependencyCheckMap(dependenciesMap);
        Session session = consulStoreClient.createSession();

        dependenciesMap.keySet()
                .forEach(k -> getCurrentAndSet(k, session));
    }

    @Override
    public Map<Dependent, List<DependencyCheckService>> validateAndMakeDependencies(Set<Class<? extends Dependencies>> dependencies) {
        Map<Dependent, List<DependencyCheckService>> ret = new EnumMap<>(Dependent.class);
        for (Class<? extends Dependencies> clazz : dependencies) {
            if (!clazz.isAnnotationPresent(Dependencies.class)) {
                throw new IllegalStateException(
                        "Declared class `%s` does not contain required `Dependencies` annotation!"
                                .formatted(clazz.getSimpleName()));
            }

            Dependencies deps = clazz.getAnnotation(Dependencies.class);
            String name = deps.dependent();
            if (!checkServiceMapping.containsKey(name)) throw new IllegalStateException("Service '%s' does not exist!"
                    .formatted(name));
            for (String dep : deps.dependencies()) {
                if (!Dependent.contains(dep)) {
                    throw new IllegalStateException("Dependent '%s' does not have a mapping!".formatted(dep));
                }
                Dependent dependent = Dependent.getByName(dep);
                if (ret.containsKey(dependent)) {
                    ret.get(dependent).add(checkServiceMapping.get(name));
                    continue;
                }
                List<DependencyCheckService> tmp = new ArrayList<>();
                tmp.add(checkServiceMapping.get(name));
                ret.put(dependent, tmp);
            }
        }
        return ret;
    }

    @Override
    public void getCurrentAndSet(Dependent key, Session session) {
        Set<String> tmp = new HashSet<>();
        try {
            tmp = DependencyUtils.decodeBase64(
                    consulStoreClient.getCurrentValue(key.getName()).getFirst().getValue());
        } catch (Exception e) {
            // ignored
        }
        Set<String> currentVal = tmp;
        currentVal.add(dependencyConfig.getServiceName());
        Failsafe.with(dependencyConfig.getPolicy()).run(() -> createOrUpdateKey(currentVal, key.getName(), session));
    }

    private Map<String, DependencyCheckService> buildMapping(List<DependencyCheckService> dependencyCheckServices) {
        return dependencyCheckServices.stream()
                .collect(Collectors.toMap(DependencyCheckService::getServiceName, service -> service));
    }

}
