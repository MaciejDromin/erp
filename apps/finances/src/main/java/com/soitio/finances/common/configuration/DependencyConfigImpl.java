package com.soitio.finances.common.configuration;

import com.soitio.commons.dependency.Dependencies;
import com.soitio.commons.dependency.DependencyConfig;
import com.soitio.commons.dependency.exception.DependencyLockException;
import dev.failsafe.Policy;
import dev.failsafe.RetryPolicy;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DependencyConfigImpl implements DependencyConfig {

    @Value("${soitio.service}")
    private String serviceName;

    @Value("${soitio.dependencies}")
    private List<String> dependencies;

    @Override
    public String getServiceName() {
        return this.serviceName;
    }

    @Override
    public Set<Class<? extends Dependencies>> getClassesWithDependencies() {
        Set<Class<? extends Dependencies>> ret = new HashSet<>();
        for (String dep : dependencies) {
            try {
                Class<?> tmp = Class.forName(dep);
                if (tmp.isAnnotationPresent(Dependencies.class)) ret.add((Class<? extends Dependencies>) tmp);
                else throw new IllegalStateException("Class '%s' does not contain required annotation!".formatted(dep));
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        return ret;
    }

    @Override
    public Policy<Object> getPolicy() {
        return RetryPolicy.builder()
                .handle(DependencyLockException.class)
                .withDelay(Duration.ofSeconds(2))
                .withMaxRetries(5)
                .build();
    }
}
