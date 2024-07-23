package pl.mlisowski.inventory.property.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import pl.mlisowski.inventory.common.PageDto;
import pl.mlisowski.inventory.property.domain.Property;
import pl.mlisowski.inventory.property.domain.dto.PropertyCreationDto;
import pl.mlisowski.inventory.property.domain.dto.PropertyForListDto;
import pl.mlisowski.inventory.property.information.PropertyInformation;
import pl.mlisowski.inventory.property.information.dto.PropertyInformationCreationDto;
import pl.mlisowski.inventory.property.information.strategy.PropertyInformationCreationProvider;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
                .landRegister(propertyCreation.getLandRegister())
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
        var objectIdsString = params.getFirst("objectIds");
        PanacheQuery<Property> properties;
        if (objectIdsString == null) properties = findAll();
        else {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) properties = findAll();
            else {
                properties = findAllByIdsNotIn(objectIds.stream()
                        .map(ObjectId::new)
                        .collect(Collectors.toSet()));
            }
        }
        var propertyList = properties.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(propertyList.stream()
                .map(this::to)
                .toList(), properties.pageCount());
    }

    public PanacheQuery<Property> findAllByIdsNotIn(Set<ObjectId> itemIds) {
        return find("{_id: { $nin: [?1]}}", itemIds);
    }

    private PropertyForListDto to(Property property) {
        return PropertyForListDto.builder()
                .id(property.getId())
                .name(property.getName())
                .uniqueIdentifier(property.getUniqueIdentifier())
                .propertyType(property.getPropertyInformation().getPropertyType())
                .landRegister(property.getLandRegister())
                .build();
    }

    public Map<String, Integer> findCountByObjectsIds(List<String> itemIds) {
        return itemIds.stream()
                .collect(Collectors.toMap(id -> id, id -> 1));
    }

}
