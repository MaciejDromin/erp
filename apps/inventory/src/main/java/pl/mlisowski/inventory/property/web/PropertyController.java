package pl.mlisowski.inventory.property.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.common.PageDto;
import pl.mlisowski.inventory.item.domain.dto.ObjectIdsDto;
import pl.mlisowski.inventory.property.application.PropertyRepository;
import pl.mlisowski.inventory.property.domain.dto.PropertyCreationDto;
import pl.mlisowski.inventory.property.domain.dto.PropertyForListDto;

import java.util.Map;

@Path("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyRepository propertyRepository;

    @GET
    public PageDto<PropertyForListDto> getProperties(@Context UriInfo uriInfo) {
        return propertyRepository.getForList(uriInfo);
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

}
