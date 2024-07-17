package pl.mlisowski.inventory.property.address.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.property.address.domain.PropertyAddress;
import pl.mlisowski.inventory.property.address.domain.dto.PropertyAddressCreationDto;
import pl.mlisowski.inventory.property.address.domain.dto.PropertyAddressDto;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class PropertyAddressRepository implements PanacheMongoRepository<PropertyAddress> {

    public void create(PropertyAddressCreationDto propertyAddressCreation) {
        persist(from(propertyAddressCreation));
    }

    private PropertyAddress from(PropertyAddressCreationDto propertyAddressCreation) {
        return PropertyAddress.builder()
                .addressLine(propertyAddressCreation.getAddressLine())
                .city(propertyAddressCreation.getCity())
                .province(propertyAddressCreation.getProvince())
                .country(propertyAddressCreation.getCountry())
                .postalCode(propertyAddressCreation.getPostalCode())
                .build();
    }

    public List<PropertyAddressDto> getAll() {
        return findAll().stream()
                .map(this::to)
                .toList();
    }

    private PropertyAddressDto to(PropertyAddress propertyAddress) {
        return PropertyAddressDto.builder()
                .id(propertyAddress.getId())
                .addressLine(propertyAddress.getAddressLine())
                .city(propertyAddress.getCity())
                .province(propertyAddress.getProvince())
                .country(propertyAddress.getCountry())
                .postalCode(propertyAddress.getPostalCode())
                .build();
    }

}
