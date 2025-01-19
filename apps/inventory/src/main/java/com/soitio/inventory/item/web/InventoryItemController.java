package com.soitio.inventory.item.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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

@Path("/items")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemRepository inventoryItemRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PageDto<InventoryItemDto> getAllItems(@Context UriInfo uriInfo, @HeaderParam("X-OrgId") String orgId) {
        return inventoryItemRepository.listAllItems(uriInfo, orgId);
    }

    @GET
    @Path("/{itemId}")
    public InventoryItemDto getItem(@PathParam("itemId") String itemId, @HeaderParam("X-OrgId") String orgId) {
        return inventoryItemRepository.getItem(itemId, orgId);
    }

    @POST
    public void createItem(ItemCreationDto itemCreation, @HeaderParam("X-OrgId") String orgId) {
        inventoryItemRepository.create(itemCreation, orgId);
    }

    @POST
    @Path("/object-names")
    public Map<String, String> objectNames(ObjectIdsDto objectNames, @HeaderParam("X-OrgId") String orgId) {
        return inventoryItemRepository.findAllItemNamesByIds(objectNames.getItemIds(), orgId);
    }

    @POST
    @Path("/object-count")
    public Map<String, Integer> objectCount(ObjectIdsDto objectIds, @HeaderParam("X-OrgId") String orgId) {
        return inventoryItemRepository.findCountByObjectsIds(objectIds.getItemIds(), orgId);
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids, @HeaderParam("X-OrgId") String orgId) {
        return inventoryItemRepository.delete(Dependent.INVENTORY_ITEM, ids, orgId);
    }

    @PATCH
    @Path("/{itemId}")
    public void updateSingleItem(@PathParam("itemId") String itemId,
                                 JsonNode json,
                                 @HeaderParam("X-OrgId") String orgId) {
        inventoryItemRepository.update(Dependent.INVENTORY_ITEM, itemId, json, orgId);
    }

}
