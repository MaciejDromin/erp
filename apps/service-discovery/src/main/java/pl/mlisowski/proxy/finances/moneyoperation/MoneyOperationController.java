package pl.mlisowski.proxy.finances.moneyoperation;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/proxy/finances/money-operation")
public class MoneyOperationController {

    @RestClient
    private MoneyOperationClient moneyOperationClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return moneyOperationClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void createCategory(Object creationRequest) {
        moneyOperationClient.create(creationRequest);
    }

}
