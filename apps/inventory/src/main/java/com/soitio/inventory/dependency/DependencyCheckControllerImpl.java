package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.DependencyCheckMap;
import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.web.DependencyCheckControllerBaseImpl;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/dependencies")
public class DependencyCheckControllerImpl extends DependencyCheckControllerBaseImpl {

    public DependencyCheckControllerImpl(DependencyCheckMap dependencyCheckMap) {
        super(dependencyCheckMap);
    }

    @Override
    @POST
    public DependencyCheckResponse check(DependencyCheckRequest dependencyCheckRequest) {
        return this.handle(dependencyCheckRequest);
    }

}
