package pl.mlisowski.inventory.property.address.web;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.property.address.application.PropertyAddressRepository;
import pl.mlisowski.inventory.property.address.domain.dto.PropertyAddressCreationDto;
import pl.mlisowski.inventory.property.address.domain.dto.PropertyAddressDto;
import java.util.List;

@Path("/addresses")
@RequiredArgsConstructor
public class PropertyAddressController {

    private final PropertyAddressRepository propertyAddressRepository;

    @GET
    public List<PropertyAddressDto> getAll() {
        return propertyAddressRepository.getAll();
    }

    @POST
    public void create(PropertyAddressCreationDto propertyAddressCreation) {
        propertyAddressRepository.create(propertyAddressCreation);
    }

}
