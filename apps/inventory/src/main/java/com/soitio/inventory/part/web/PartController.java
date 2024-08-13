package com.soitio.inventory.part.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import com.soitio.inventory.common.PageDto;
import com.soitio.inventory.part.application.PartRepository;
import com.soitio.inventory.part.domain.dto.PartCreationDto;
import com.soitio.inventory.part.domain.dto.PartDto;

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

}
