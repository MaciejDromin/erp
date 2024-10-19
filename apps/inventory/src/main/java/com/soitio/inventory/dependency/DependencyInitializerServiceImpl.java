package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.DependencyCheckMap;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.DependencyConfig;
import com.soitio.commons.dependency.DependencyInitializerBaseService;
import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Dependent
@Slf4j
public class DependencyInitializerServiceImpl extends DependencyInitializerBaseService {

    public DependencyInitializerServiceImpl(@RestClient ConsulStoreClientIntf consulStoreClient,
                                            DependencyConfig dependencyConfig,
                                            @All List<DependencyCheckService> dependencyCheckServices) {
        super(consulStoreClient, dependencyConfig, dependencyCheckServices);
    }

    @Override
    @Produces
    public DependencyCheckMap produceDependencyMap() {
        this.initializeDependencies();
        return this.dependencyCheckMap;
    }

}
