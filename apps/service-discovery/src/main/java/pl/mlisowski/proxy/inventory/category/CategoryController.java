package pl.mlisowski.proxy.inventory.category;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/proxy/inventory/categories")
public class CategoryController {

    @RestClient
    private CategoryClient categoryClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return categoryClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void createCategory(Object creationRequest) {
        categoryClient.create(creationRequest);
    }

}
