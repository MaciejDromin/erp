package com.soitio.inventory.property.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import com.soitio.inventory.property.information.dto.PropertyInformationCreationDto;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class PropertyCreationDto {

    private String name;
    private String uniqueIdentifier;
    private ObjectId addressId;
    private String landRegister;
    private PropertyInformationCreationDto propertyInformation;

}
