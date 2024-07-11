package com.soitio.widgets.finances.client;

import com.soitio.widgets.finances.domain.TotalObjectsValueDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;
import java.util.Map;

@Path("/finances")
@RegisterRestClient
public interface FinancesClient {

    @GET
    @Path("/object-value/object-ids")
    List<String> allObjectIds();

    @POST
    @Path("/object-value/total-value")
    TotalObjectsValueDto totalValue(Map<String, Integer> quantityByObjectMap);

}
