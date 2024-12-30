package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.client.DependencyKeyClient;
import com.soitio.commons.dependency.model.StoreKey;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.Set;

@Path("/store")
@RegisterRestClient(configKey = "gateway-api")
public interface DependencyKeyClientIntf extends DependencyKeyClient {

    @Override
    @PUT
    @Path("/{key}")
    boolean updateKey(@PathParam("key") String s,
                      Set<String> set);

    @Override
    @GET
    @Path("/{key}")
    StoreKey getCurrentValue(@PathParam("key") String s);

}
