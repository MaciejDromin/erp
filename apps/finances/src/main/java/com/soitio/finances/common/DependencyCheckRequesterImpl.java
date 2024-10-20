package com.soitio.finances.common;

import com.soitio.commons.dependency.DependencyCheckRequesterBaseImpl;
import com.soitio.commons.dependency.client.ConsulStoreClient;
import com.soitio.commons.dependency.client.DependencyCheckClient;
import org.springframework.stereotype.Service;

@Service
public class DependencyCheckRequesterImpl extends DependencyCheckRequesterBaseImpl {

    protected DependencyCheckRequesterImpl(ConsulStoreClient consulStoreClient, DependencyCheckClient dependencyCheckClient) {
        super(consulStoreClient, dependencyCheckClient);
    }

}
