package com.soitio.reports.service.inventory.client;

import com.soitio.commons.models.dto.PageDto;
import com.soitio.reports.service.inventory.domain.InventoryItemDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;
import java.util.Map;

@Path("/")
@RegisterRestClient
public interface InventoryClient {

    @GET
    @Path("/items")
    PageDto<InventoryItemDto> getAllItems(@RestQuery Map<String, String> params);

}
