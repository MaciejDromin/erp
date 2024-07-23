package com.soitio.widgets.finances.inventory.client;

import com.soitio.widgets.finances.inventory.domain.ObjectIdsDto;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.Map;

@Path("/")
@RegisterRestClient
public interface InventoryClient {

    @POST
    @Path("/inventory/items/object-count")
    Map<String, Integer> itemCount(ObjectIdsDto objectIds);

    @POST
    @Path("properties/object-count")
    Map<String, Integer> propertyCount(ObjectIdsDto objectIds);

}
