package com.soitio.inventory.property.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import com.soitio.inventory.common.PageDto;
import com.soitio.inventory.property.domain.Property;
import com.soitio.inventory.property.domain.dto.PropertyCreationDto;
import com.soitio.inventory.property.domain.dto.PropertyForListDto;
import com.soitio.inventory.property.information.PropertyInformation;
import com.soitio.inventory.property.information.dto.PropertyInformationCreationDto;
import com.soitio.inventory.property.information.strategy.PropertyInformationCreationProvider;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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

    public Map<String, String> findAllItemNamesByIds(List<String> itemIds) {
        Map<String, String> ret = new HashMap<>();
        var items = findAllByIdsIn(itemIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toSet()));
        items.forEach(item -> ret.put(item.getId().toString(), item.getName()));
        return ret;
    }

    public Set<Property> findAllByIdsIn(Set<ObjectId> itemIds) {
        return new HashSet<>(list("_id in ?1", itemIds));
    }

}
