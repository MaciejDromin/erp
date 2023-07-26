package pl.mlisowski.proxy.finances.currency;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/finances/currencies")
@RegisterRestClient(baseUri = "stork://finances")
public interface CurrencyClient {

    @GET
    Object getAll(@RestQuery MultivaluedMap<String, String> queryParams);

}
