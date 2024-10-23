package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.DependencyConfig;
import com.soitio.commons.dependency.DependencyInitializer;
import io.quarkus.arc.All;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

//@Dependent
public class DependencyStartup {

//    void startup(@Observes StartupEvent event,
//                 @RestClient ConsulStoreClientIntf consulStoreClient,
//                 DependencyConfig dependencyConfig,
//                 @All List<DependencyCheckService> dependencyCheckServices) {
//        this.dependencyInitializer(consulStoreClient, dependencyConfig, dependencyCheckServices);
//    }
//
//    @Produces
//    public DependencyInitializer dependencyInitializer(@RestClient ConsulStoreClientIntf consulStoreClient,
//                                                       DependencyConfig dependencyConfig,
//                                                       @All List<DependencyCheckService> dependencyCheckServices) {
//        var depInit = new DependencyInitializerServiceImpl(consulStoreClient, dependencyConfig, dependencyCheckServices);
//        depInit.initializeDependencies();
//        return depInit;
//    }
}
