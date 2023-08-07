package pl.mlisowski.proxy.planner.materials;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/proxy/planner/materials")
public class MaterialsController {

    @RestClient
    private MaterialsClient materialsClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return materialsClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void createMaterial(Object creationRequest) {
        materialsClient.create(creationRequest);
    }

}
