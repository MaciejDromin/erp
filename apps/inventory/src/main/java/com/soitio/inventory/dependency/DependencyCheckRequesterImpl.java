package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.DependencyCheckRequesterBaseImpl;
import com.soitio.commons.dependency.client.DependencyCheckClient;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Singleton
public class DependencyCheckRequesterImpl extends DependencyCheckRequesterBaseImpl {

    public DependencyCheckRequesterImpl(@RestClient DependencyKeyClientIntf consulStoreClient,
                                        DependencyCheckClient dependencyCheckClient) {
        super(consulStoreClient, dependencyCheckClient);
    }

}
