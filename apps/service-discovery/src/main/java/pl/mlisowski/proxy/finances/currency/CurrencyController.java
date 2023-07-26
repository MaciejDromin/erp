package pl.mlisowski.proxy.finances.currency;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/proxy/finances/currencies")
public class CurrencyController {

    @RestClient
    private CurrencyClient currencyClient;

    @GET
    public Object getAll(@Context UriInfo uriInfo) {
        return currencyClient.getAll(uriInfo.getQueryParameters());
    }

}
