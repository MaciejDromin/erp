package com.soitio.inventory.property.information.strategy;

import com.soitio.commons.models.commons.MergePatch;
import com.soitio.inventory.property.information.PropertyInformation;
import com.soitio.inventory.property.information.PropertyType;
import com.soitio.inventory.property.information.dto.PropertyInformationCreationDto;
import com.soitio.inventory.property.information.dto.PropertyInformationDto;

public interface PropertyInformationStrategy<D extends PropertyInformationDto,
                                             T extends PropertyInformation> {

    boolean isApplicable(PropertyType type);

    T create(PropertyInformationCreationDto propertyInformationCreation);

    PropertyInformation jsonToEntity(MergePatch value);

    D from(PropertyInformation propertyInformation);

}
