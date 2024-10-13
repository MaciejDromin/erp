package com.soitio.finances.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckMap;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.DependencyConfig;
import com.soitio.commons.dependency.DependencyInitializerBaseService;
import com.soitio.commons.dependency.client.ConsulStoreClient;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class DependencyInitializerImpl extends DependencyInitializerBaseService {
    protected DependencyInitializerImpl(ConsulStoreClient consulStoreClient,
                                        ObjectMapper objectMapper,
                                        DependencyConfig dependencyConfig,
                                        List<DependencyCheckService> dependencyCheckServices) {
        super(consulStoreClient, objectMapper, dependencyConfig, dependencyCheckServices);
    }

    @Override
    @Bean
    public DependencyCheckMap produceDependencyMap() {
        return this.dependencyCheckMap;
    }
}
