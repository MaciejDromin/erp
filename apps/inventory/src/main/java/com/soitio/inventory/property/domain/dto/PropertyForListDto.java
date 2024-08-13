package com.soitio.inventory.property.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import com.soitio.inventory.property.information.PropertyType;

@Builder
@AllArgsConstructor
@Getter
public class PropertyForListDto {

    private ObjectId id;
    private String name;
    private String uniqueIdentifier;
    private String landRegister;
    private PropertyType propertyType;

}
