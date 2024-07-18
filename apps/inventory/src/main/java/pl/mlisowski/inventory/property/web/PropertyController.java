package pl.mlisowski.inventory.property.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.mlisowski.inventory.common.PageDto;
import pl.mlisowski.inventory.property.application.PropertyRepository;
import pl.mlisowski.inventory.property.domain.dto.PropertyCreationDto;
import pl.mlisowski.inventory.property.domain.dto.PropertyForListDto;

@Path("/properties")
@RequiredArgsConstructor
@Slf4j
public class PropertyController {

    private final PropertyRepository propertyRepository;

    @GET
    public PageDto<PropertyForListDto> getProperties(@Context UriInfo uriInfo) {
        return propertyRepository.getForList(uriInfo);
    }

    @POST
    public void createProperty(PropertyCreationDto propertyCreation) {
        log.info("ML --- TEST {}", propertyCreation);
        propertyRepository.create(propertyCreation);
    }

}
