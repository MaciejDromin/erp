package com.soitio.inventory.property.domain.dto;

import com.soitio.inventory.property.information.dto.PropertyInformationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class PropertyDto {

    private ObjectId id;
    private String name;
    private String uniqueIdentifier;
    private ObjectId addressId;
    private String landRegister;
    private PropertyInformationDto propertyInformation;

}
