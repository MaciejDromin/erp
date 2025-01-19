package com.soitio.inventory.property.address.web;

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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.property.address.application.PropertyAddressRepository;
import com.soitio.inventory.property.address.domain.dto.PropertyAddressCreationDto;
import com.soitio.inventory.property.address.domain.dto.PropertyAddressDto;

import java.util.Set;

@Path("/addresses")
@RequiredArgsConstructor
public class PropertyAddressController {

    private final PropertyAddressRepository propertyAddressRepository;

    @GET
    public PageDto<PropertyAddressDto> getAll(@Context UriInfo uriInfo, @HeaderParam("X-OrgId") String orgId) {
        return propertyAddressRepository.getAll(uriInfo, orgId);
    }

    @GET
    @Path("/{addressId}")
    public PropertyAddressDto getSingleAddress(@PathParam("addressId") String id,
                                               @HeaderParam("X-OrgId") String orgId) {
        return propertyAddressRepository.getAddress(id, orgId);
    }

    @POST
    public void create(PropertyAddressCreationDto propertyAddressCreation,
                       @HeaderParam("X-OrgId") String orgId) {
        propertyAddressRepository.create(propertyAddressCreation, orgId);
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids, @HeaderParam("X-OrgId") String orgId) {
        return propertyAddressRepository.delete(Dependent.INVENTORY_ADDRESS, ids, orgId);
    }

    @PATCH
    @Path("/{addressId}")
    public void updateSingleProperty(@PathParam("addressId") String addressId,
                                     JsonNode node,
                                     @HeaderParam("X-OrgId") String orgId) {
        propertyAddressRepository.update(Dependent.INVENTORY_ADDRESS, addressId, node, orgId);
    }

}
