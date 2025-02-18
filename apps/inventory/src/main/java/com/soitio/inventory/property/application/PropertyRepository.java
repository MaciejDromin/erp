package com.soitio.inventory.property.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import com.soitio.inventory.property.domain.dto.PropertyDto;
import com.soitio.inventory.property.information.PropertyType;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.property.domain.Property;
import com.soitio.inventory.property.domain.dto.PropertyCreationDto;
import com.soitio.inventory.property.domain.dto.PropertyForListDto;
import com.soitio.inventory.property.information.PropertyInformation;
import com.soitio.inventory.property.information.dto.PropertyInformationCreationDto;
import com.soitio.inventory.property.information.strategy.PropertyInformationProvider;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PropertyRepository extends AbstractDependencyCheckRepo<Property> implements DependencyCheckService {

    private static final String SERVICE_NAME = "Property";
    private static final Integer DEFAULT_PAGE_SIZE = 20;

    private final PropertyInformationProvider propertyProvider;

    public PropertyRepository(ObjectMapper mapper,
                              DependencyCheckRequester dependencyCheckRequester,
                              PropertyInformationProvider propertyProvider) {
        super(mapper, dependencyCheckRequester);
        this.propertyProvider = propertyProvider;
    }

    public void create(PropertyCreationDto propertyCreation, String orgId) {
        persist(from(propertyCreation, orgId));
    }

    private Property from(PropertyCreationDto propertyCreation, String orgId) {
        return Property.builder()
                .name(propertyCreation.getName())
                .uniqueIdentifier(propertyCreation.getUniqueIdentifier())
                .addressId(propertyCreation.getAddressId())
                .landRegister(propertyCreation.getLandRegister())
                .propertyInformation(fromPropertyInformation(propertyCreation.getPropertyInformation()))
                .orgId(orgId)
                .build();
    }

    private PropertyInformation fromPropertyInformation(PropertyInformationCreationDto propertyInformation) {
        return propertyProvider.get(propertyInformation.getPropertyType())
                .create(propertyInformation);
    }

    public PageDto<PropertyForListDto> getForList(UriInfo uriInfo, String orgId) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var objectIdsString = params.getFirst("objectIds");
        PanacheQuery<Property> properties;
        if (objectIdsString == null) properties = findAllByOrgId(orgId);
        else {
            List<String> objectIds = Arrays.asList(objectIdsString.split(","));
            if (objectIds.isEmpty()) properties = findAllByOrgId(orgId);
            else {
                properties = findAllByIdsNotInAndOrgId(objectIds, orgId);
            }
        }
        var propertyList = properties.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(propertyList.stream()
                .map(this::to)
                .toList(), properties.pageCount());
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

    public Map<String, Integer> findCountByObjectsIds(List<String> itemIds, String orgId) { // What happened here?
        return itemIds.stream()
                .collect(Collectors.toMap(id -> id, id -> 1));
    }

    public Map<String, String> findAllItemNamesByIds(List<String> itemIds, String orgId) {
        Map<String, String> ret = new HashMap<>();
        var items = listAllByIdsInAndOrgId(itemIds, orgId);
        items.forEach(item -> ret.put(item.getId().toString(), item.getName()));
        return ret;
    }

    public Set<Property> findAllByAddressIdsIn(Set<ObjectId> ids) {
        return new HashSet<>(list("addressId in ?1", ids));
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public Set<DependencyCheckResult> checkForEdit(Set<DependencyCheckContext> set) {
        return Set.of();
    }

    @Override
    public Set<DependencyCheckResult> checkForDelete(Set<DependencyCheckContext> set) {
        return findAllByAddressIdsIn(set.stream()
                .map(DependencyCheckContext::id)
                .map(ObjectId::new)
                .collect(Collectors.toSet()))
                .stream()
                .map(p -> new DependencyCheckResult(p.getAddressId().toString(), true, "Property Adress with id '%s' is in use".formatted(p.getAddressId())))
                .collect(Collectors.toSet());
    }

    @Override
    protected Property mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        MergePatch propertyInformation = fields.get("propertyInformation");
        PropertyType type = PropertyType.valueOf(propertyInformation.getObjectValue().get("propertyType").getStrValue());
        return Property.builder()
                .id(new ObjectId(fields.get("id").getStrValue()))
                .name(fields.get("name").getStrValue())
                .uniqueIdentifier(fields.get("uniqueIdentifier").getStrValue())
                .addressId(new ObjectId(fields.get("addressId").getStrValue()))
                .landRegister(fields.get("landRegister").getStrValue())
                .propertyInformation(propertyProvider.get(type).jsonToEntity(propertyInformation))
                .build();
    }

    public PropertyDto getProperty(String id, String orgId) {
        return toDto(findByIdAndOrgId(id, orgId));
    }

    private PropertyDto toDto(Property object) {
        return PropertyDto.builder()
                .id(object.getId())
                .name(object.getName())
                .uniqueIdentifier(object.getUniqueIdentifier())
                .addressId(object.getAddressId())
                .landRegister(object.getLandRegister())
                .propertyInformation(propertyProvider.get(object.getPropertyInformation().getPropertyType())
                        .from(object.getPropertyInformation()))
                .build();
    }
}
