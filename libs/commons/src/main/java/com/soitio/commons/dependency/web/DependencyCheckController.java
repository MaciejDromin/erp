package com.soitio.commons.dependency.web;

import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;

public interface DependencyCheckController {

    DependencyCheckResponse check(DependencyCheckRequest request);

}
