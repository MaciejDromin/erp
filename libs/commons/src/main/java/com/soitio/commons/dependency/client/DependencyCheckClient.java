package com.soitio.commons.dependency.client;

import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;

import java.net.URI;

public interface DependencyCheckClient {

    DependencyCheckResponse check(URI uri, DependencyCheckRequest request);

}
