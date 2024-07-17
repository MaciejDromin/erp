package pl.mlisowski.inventory.property.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.property.application.PropertyRepository;
import pl.mlisowski.inventory.property.domain.dto.PropertyCreationDto;
import pl.mlisowski.inventory.property.domain.dto.PropertyForListDto;
import java.util.List;

@Path("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyRepository propertyRepository;

    @GET
    public List<PropertyForListDto> getProperties() {
        return propertyRepository.getForList();
    }

    @POST
    public void createProperty(PropertyCreationDto propertyCreation) {
        propertyRepository.create(propertyCreation);
    }

}
