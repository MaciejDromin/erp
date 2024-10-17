package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/dependencies")
public interface QuarkusDependencyClient {

    @POST
    DependencyCheckResponse check(DependencyCheckRequest request);

}
