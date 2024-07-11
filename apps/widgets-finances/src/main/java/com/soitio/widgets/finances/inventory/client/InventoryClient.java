package com.soitio.widgets.finances.inventory.client;

import com.soitio.widgets.finances.inventory.domain.ObjectIdsDto;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.Map;

@Path("/inventory")
@RegisterRestClient
public interface InventoryClient {

    @POST
    @Path("/items/object-count")
    public Map<String, Integer> objectCount(ObjectIdsDto objectIds);

}
