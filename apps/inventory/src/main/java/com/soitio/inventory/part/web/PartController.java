package com.soitio.inventory.part.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.model.Dependent;
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
import com.soitio.inventory.part.application.PartRepository;
import com.soitio.inventory.part.domain.dto.PartCreationDto;
import com.soitio.inventory.part.domain.dto.PartDto;

import java.util.Set;

@Path("/parts")
@RequiredArgsConstructor
public class PartController {

    private final PartRepository partRepository;

    @GET
    public PageDto<PartDto> getParts(@Context UriInfo uriInfo) {
        return partRepository.getParts(uriInfo);
    }

    @POST
    public void createPart(PartCreationDto partCreation) {
        partRepository.create(partCreation);
    }

    @DELETE
    public DependencyCheckResponse delete(Set<String> ids) {
        return partRepository.delete(Dependent.INVENTORY_PART, ids);
    }

    @PATCH
    @Path("/{partId}")
    public void updateSinglePart(@PathParam("partId") String partId, JsonNode node) {
        partRepository.update(Dependent.INVENTORY_PART, partId, node);
    }

}
