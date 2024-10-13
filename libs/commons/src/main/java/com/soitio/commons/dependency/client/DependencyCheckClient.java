package com.soitio.commons.dependency.client;

import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;

public interface DependencyCheckClient {

    DependencyCheckResponse check(DependencyCheckRequest request);

}
