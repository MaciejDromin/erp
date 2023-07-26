package pl.mlisowski.proxy.finances.moneyoperation.periodical;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/proxy/finances/money-operation/periodical")
public class PeriodicalMoneyOperationController {

    @RestClient
    private PeriodicalMoneyOperationClient periodicalMoneyOperationClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return periodicalMoneyOperationClient.getAll(uriInfo.getQueryParameters());
    }

    @POST
    public void createCategory(Object creationRequest) {
        periodicalMoneyOperationClient.create(creationRequest);
    }

}
