package pl.mlisowski.inventory.property.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.common.PageDto;
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

    private static final Integer DEFAULT_PAGE_SIZE = 20;

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

    public PageDto<PropertyForListDto> getForList(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var properties = findAll();
        var propertyList = properties.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(propertyList.stream()
                .map(this::to)
                .toList(), properties.pageCount());
    }

    private PropertyForListDto to(Property property) {
        return PropertyForListDto.builder()
                .build();
    }

}
