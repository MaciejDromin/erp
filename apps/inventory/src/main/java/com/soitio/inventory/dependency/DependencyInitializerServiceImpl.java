package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.DependencyCheckMap;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.DependencyConfig;
import com.soitio.commons.dependency.DependencyInitializerBaseService;
import io.quarkus.arc.All;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Startup
@Singleton
public class DependencyInitializerServiceImpl extends DependencyInitializerBaseService {

    public DependencyInitializerServiceImpl(@RestClient DependencyKeyClientIntf consulStoreClient,
                                            DependencyConfig dependencyConfig,
                                            @All List<DependencyCheckService> dependencyCheckServices) {
        super(consulStoreClient, dependencyConfig, dependencyCheckServices);
        this.initializeDependencies();
    }

    @Override
    @Produces
    public DependencyCheckMap produceDependencyMap() {
        return this.dependencyCheckMap;
    }

}
