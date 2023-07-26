package pl.mlisowski.proxy.inventory.item;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/inventory/items")
@RegisterRestClient(baseUri = "stork://inventory")
public interface InventoryItemClient {

    @GET
    Object getAll(@RestQuery MultivaluedMap<String, String> queryParams);

    @POST
    void create(Object creationRequest);

    @POST
    @Path("/object-names")
    Object objectNames(Object objectIds);

    @POST
    @Path("/object-count")
    Object objectCount(Object objectIds);
}
