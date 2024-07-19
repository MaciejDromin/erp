package pl.mlisowski.inventory.property.address.web;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.common.PageDto;
import pl.mlisowski.inventory.property.address.application.PropertyAddressRepository;
import pl.mlisowski.inventory.property.address.domain.dto.PropertyAddressCreationDto;
import pl.mlisowski.inventory.property.address.domain.dto.PropertyAddressDto;

@Path("/addresses")
@RequiredArgsConstructor
public class PropertyAddressController {

    private final PropertyAddressRepository propertyAddressRepository;

    @GET
    public PageDto<PropertyAddressDto> getAll(@Context UriInfo uriInfo) {
        return propertyAddressRepository.getAll(uriInfo);
    }

    @POST
    public void create(PropertyAddressCreationDto propertyAddressCreation) {
        propertyAddressRepository.create(propertyAddressCreation);
    }

}
