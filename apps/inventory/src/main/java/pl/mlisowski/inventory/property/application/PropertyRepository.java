package pl.mlisowski.inventory.property.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.property.domain.Property;
import pl.mlisowski.inventory.property.domain.dto.PropertyCreationDto;
import pl.mlisowski.inventory.property.domain.dto.PropertyForListDto;
import pl.mlisowski.inventory.property.information.PropertyInformation;
import pl.mlisowski.inventory.property.information.dto.PropertyInformationCreationDto;
import pl.mlisowski.inventory.property.information.strategy.PropertyInformationCreationProvider;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class PropertyRepository implements PanacheMongoRepository<Property> {

    private final PropertyInformationCreationProvider propertyProvider;

    public void create(PropertyCreationDto propertyCreation) {
        persist(from(propertyCreation));
    }

    private Property from(PropertyCreationDto propertyCreation) {
        return Property.builder()
                .name(propertyCreation.getName())
                .uniqueIdentifier(propertyCreation.getUniqueIdentifier())
                .addressId(propertyCreation.getAddressId())
                .propertyInformation(fromPropertyInformation(propertyCreation.getPropertyInformation()))
                .build();
    }

    private PropertyInformation fromPropertyInformation(PropertyInformationCreationDto propertyInformation) {
        return propertyProvider.map(propertyInformation);
    }

    public List<PropertyForListDto> getForList() {
        return findAll().stream()
                .map(this::to)
                .toList();
    }

    private PropertyForListDto to(Property property) {
        return PropertyForListDto.builder()
                .build();
    }

}
