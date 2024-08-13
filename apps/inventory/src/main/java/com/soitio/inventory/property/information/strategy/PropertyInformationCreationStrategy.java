package com.soitio.inventory.property.information.strategy;

import com.soitio.inventory.property.information.PropertyInformation;
import com.soitio.inventory.property.information.dto.PropertyInformationCreationDto;

public interface PropertyInformationCreationStrategy {

    boolean isApplicable(PropertyInformationCreationDto propertyInformationCreation);

    PropertyInformation map(PropertyInformationCreationDto propertyInformationCreation);

}
