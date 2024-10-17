package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.DependencyCheckRequesterBaseImpl;
import com.soitio.commons.dependency.client.ConsulStoreClient;
import com.soitio.commons.dependency.client.DependencyCheckClient;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class DependencyCheckRequesterImpl extends DependencyCheckRequesterBaseImpl {

    public DependencyCheckRequesterImpl(@RestClient ConsulStoreClientIntf consulStoreClient,
                                        DependencyCheckClient dependencyCheckClient) {
        super(consulStoreClient, dependencyCheckClient);
    }

}
