package com.soitio.inventory.property.information.dto;

import com.soitio.inventory.property.information.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class PropertyInformationDto {

    private PropertyType propertyType;

}
