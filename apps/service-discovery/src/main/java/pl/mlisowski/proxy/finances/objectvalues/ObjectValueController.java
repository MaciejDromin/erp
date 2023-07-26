package pl.mlisowski.proxy.finances.objectvalues;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/proxy/finances/object-value")
public class ObjectValueController {

    @RestClient
    private ObjectValueClient objectValueClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return objectValueClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void createCategory(Object creationRequest) {
        objectValueClient.create(creationRequest);
    }

    @POST
    @Path("/total-value")
    public Object totalValue(Object quantityPerObjectMap) {
        return objectValueClient.totalValue(quantityPerObjectMap);
    }

    @GET
    @Path("object-ids")
    public Object allObjectIds() {
        return objectValueClient.allObjectIds();
    }

}
