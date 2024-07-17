package pl.mlisowski.inventory.property.address.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PropertyAddressCreationDto {

    private String addressLine;
    private String city;
    private String country;
    private String province;
    private String postalCode;

}
