package com.soitio.reports.service.inventory.client;

import com.soitio.commons.models.dto.PageDto;
import com.soitio.reports.service.inventory.domain.InventoryItemDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/inventory")
@RegisterRestClient
public interface InventoryClient {

    @GET
    @Path("/items")
    PageDto<InventoryItemDto> getAllItems(@QueryParam("page") Integer page, @QueryParam("size") Integer size);

}
