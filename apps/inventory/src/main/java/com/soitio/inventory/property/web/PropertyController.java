package com.soitio.inventory.property.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
import com.soitio.inventory.property.domain.dto.PropertyDto;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.item.domain.dto.ObjectIdsDto;
import com.soitio.inventory.property.application.PropertyRepository;
import com.soitio.inventory.property.domain.dto.PropertyCreationDto;
import com.soitio.inventory.property.domain.dto.PropertyForListDto;

import java.util.Map;
import java.util.Set;

@Path("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyRepository propertyRepository;

    @GET
    public PageDto<PropertyForListDto> getProperties(@Context UriInfo uriInfo) {
        return propertyRepository.getForList(uriInfo);
    }

    @GET
    @Path("/{propertyId}")
    public PropertyDto getProperty(@PathParam("propertyId") String id) {
        return propertyRepository.getProperty(id);
    }

    @POST
    public void createProperty(PropertyCreationDto propertyCreation) {
        propertyRepository.create(propertyCreation);
    }

    @POST
    @Path("/object-count")
    public Map<String, Integer> objectCount(ObjectIdsDto objectIds) {
        return propertyRepository.findCountByObjectsIds(objectIds.getItemIds());
    }

    @POST
    @Path("/object-names")
    public Map<String, String> objectNames(ObjectIdsDto objectNames) {
        return propertyRepository.findAllItemNamesByIds(objectNames.getItemIds());
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids) {
        return propertyRepository.delete(Dependent.INVENTORY_PROPERTY, ids);
    }

    @PATCH
    @Path("/{propertyId}")
    public void updateSingleProperty(@PathParam("propertyId") String propertyId, JsonNode node) {
        propertyRepository.update(Dependent.INVENTORY_PROPERTY, propertyId, node);
    }

}
