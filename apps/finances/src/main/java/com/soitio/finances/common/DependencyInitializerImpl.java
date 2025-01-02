package com.soitio.finances.common;

import com.soitio.commons.dependency.DependencyCheckMap;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.DependencyConfig;
import com.soitio.commons.dependency.DependencyInitializerBaseService;
import com.soitio.commons.dependency.client.DependencyKeyClient;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class DependencyInitializerImpl extends DependencyInitializerBaseService {

    protected DependencyInitializerImpl(DependencyKeyClient dependencyKeyClient,
                                        DependencyConfig dependencyConfig,
                                        List<DependencyCheckService> dependencyCheckServices) {
        super(dependencyKeyClient, dependencyConfig, dependencyCheckServices);
    }

    @Override
    @Bean
    public DependencyCheckMap produceDependencyMap() {
        this.initializeDependencies();
        return this.dependencyCheckMap;
    }
}
