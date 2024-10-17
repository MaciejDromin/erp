package com.soitio.inventory.item.web;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.inventory.item.InventoryItemDto;
import com.soitio.inventory.item.application.InventoryItemRepository;
import com.soitio.inventory.item.domain.dto.ItemCreationDto;
import com.soitio.inventory.item.domain.dto.ObjectIdsDto;
import java.util.Map;
import java.util.Set;

@Path("/inventory/items")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemRepository inventoryItemRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PageDto<InventoryItemDto> getAllItems(@Context UriInfo uriInfo) {
        return inventoryItemRepository.listAllItems(uriInfo);
    }

    @POST
    public void createItem(ItemCreationDto itemCreation) {
        inventoryItemRepository.create(itemCreation);
    }

    @POST
    @Path("/object-names")
    public Map<String, String> objectNames(ObjectIdsDto objectNames) {
        return inventoryItemRepository.findAllItemNamesByIds(objectNames.getItemIds());
    }

    @POST
    @Path("/object-count")
    public Map<String, Integer> objectCount(ObjectIdsDto objectIds) {
        return inventoryItemRepository.findCountByObjectsIds(objectIds.getItemIds());
    }

    @DELETE
    public void delete(Set<String> ids) {
        inventoryItemRepository.delete(ids);
    }

}
