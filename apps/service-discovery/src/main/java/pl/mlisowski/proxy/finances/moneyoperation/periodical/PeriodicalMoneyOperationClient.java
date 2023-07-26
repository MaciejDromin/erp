package pl.mlisowski.proxy.finances.moneyoperation.periodical;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/finances/money-operation/periodical")
@RegisterRestClient(baseUri = "stork://finances")
public interface PeriodicalMoneyOperationClient {

    @GET
    Object getAll(@RestQuery MultivaluedMap<String, String> queryParams);

    @POST
    void create(Object creationRequest);

}
