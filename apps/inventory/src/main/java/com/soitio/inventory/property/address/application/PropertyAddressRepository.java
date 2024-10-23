package com.soitio.inventory.property.address.application;

import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.inventory.dependency.AbstractDependencyCheckRepo;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriInfo;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.inventory.property.address.domain.PropertyAddress;
import com.soitio.inventory.property.address.domain.dto.PropertyAddressCreationDto;
import com.soitio.inventory.property.address.domain.dto.PropertyAddressDto;

@Singleton
public class PropertyAddressRepository extends AbstractDependencyCheckRepo<PropertyAddress> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public PropertyAddressRepository(DependencyCheckRequester dependencyCheckRequester) {
        super(dependencyCheckRequester);
    }

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

    public PageDto<PropertyAddressDto> getAll(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var addresses = findAll();
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

}
