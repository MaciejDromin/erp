package com.soitio.finances.common;

import com.soitio.commons.dependency.DependencyCheckRequesterBaseImpl;
import com.soitio.commons.dependency.client.DependencyCheckClient;
import com.soitio.commons.dependency.client.DependencyKeyClient;
import org.springframework.stereotype.Service;

@Service
public class DependencyCheckRequesterImpl extends DependencyCheckRequesterBaseImpl {

    protected DependencyCheckRequesterImpl(DependencyKeyClient dependencyKeyClient,
                                           DependencyCheckClient dependencyCheckClient) {
        super(dependencyKeyClient, dependencyCheckClient);
    }

}
