package pl.mlisowski.inventory.property.address.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Builder
@AllArgsConstructor
@Getter
public class PropertyAddressDto {

    private ObjectId id;
    private String addressLine;
    private String city;
    private String country;
    private String province;
    private String postalCode;

}
