package com.soitio.commons.dependency;

import com.soitio.commons.dependency.model.Action;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import java.util.Set;

public interface DependencyCheckRequester {

    DependencyCheckResponse requestDependencyCheckForIds(Dependent dependent, Set<DependencyCheckContext> context, Action action);

}
