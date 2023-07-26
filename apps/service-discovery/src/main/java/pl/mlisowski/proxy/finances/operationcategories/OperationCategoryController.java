package pl.mlisowski.proxy.finances.operationcategories;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/proxy/finances/operation-category")
public class OperationCategoryController {

    @RestClient
    private OperationCategoryClient operationCategoryClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return operationCategoryClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void createCategory(Object creationRequest) {
        operationCategoryClient.create(creationRequest);
    }

}
