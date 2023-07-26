package pl.mlisowski.proxy.inventory.item;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Slf4j
@Path("/proxy/inventory/items")
public class InventoryItemController {

    @RestClient
    private InventoryItemClient inventoryItemClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return inventoryItemClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void createItem(Object creationRequest) {
        inventoryItemClient.create(creationRequest);
    }

    @POST
    @Path("/object-names")
    public Object itemNames(Object objectIds) {
        return inventoryItemClient.objectNames(objectIds);
    }

    @POST
    @Path("/object-count")
    public Object itemCount(Object objectIds) {
        return inventoryItemClient.objectCount(objectIds);
    }

}
