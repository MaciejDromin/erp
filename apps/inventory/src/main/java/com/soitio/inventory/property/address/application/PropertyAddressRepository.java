package com.soitio.inventory.property.address.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriInfo;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.property.address.domain.PropertyAddress;
import com.soitio.inventory.property.address.domain.dto.PropertyAddressCreationDto;
import com.soitio.inventory.property.address.domain.dto.PropertyAddressDto;
import org.bson.types.ObjectId;

@Singleton
public class PropertyAddressRepository extends AbstractDependencyCheckRepo<PropertyAddress> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public PropertyAddressRepository(ObjectMapper mapper,
                                     DependencyCheckRequester dependencyCheckRequester) {
        super(mapper, dependencyCheckRequester);
    }

    public void create(PropertyAddressCreationDto propertyAddressCreation, String orgId) {
        persist(from(propertyAddressCreation, orgId));
    }

    private PropertyAddress from(PropertyAddressCreationDto propertyAddressCreation, String orgId) {
        return PropertyAddress.builder()
                .addressLine(propertyAddressCreation.getAddressLine())
                .city(propertyAddressCreation.getCity())
                .province(propertyAddressCreation.getProvince())
                .country(propertyAddressCreation.getCountry())
                .postalCode(propertyAddressCreation.getPostalCode())
                .orgId(orgId)
                .build();
    }

    public PageDto<PropertyAddressDto> getAll(UriInfo uriInfo, String orgId) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var addresses = findAllByOrgId(orgId);
        var addressList = addresses.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(addressList.stream()
                .map(this::to)
                .toList(), addresses.pageCount());
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

    @Override
    protected PropertyAddress mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        return PropertyAddress.builder()
                .id(new ObjectId(fields.get("id").getStrValue()))
                .addressLine(fields.get("addressLine").getStrValue())
                .city(fields.get("city").getStrValue())
                .province(fields.get("province").getStrValue())
                .country(fields.get("country").getStrValue())
                .postalCode(fields.get("postalCode").getStrValue())
                .build();
    }

    public PropertyAddressDto getAddress(String id, String orgId) {
        return to(findByIdAndOrgId(id, orgId));
    }
}
