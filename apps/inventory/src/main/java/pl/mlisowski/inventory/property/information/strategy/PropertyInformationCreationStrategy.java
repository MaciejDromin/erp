package pl.mlisowski.inventory.property.information.strategy;

import pl.mlisowski.inventory.property.information.PropertyInformation;
import pl.mlisowski.inventory.property.information.dto.PropertyInformationCreationDto;

public interface PropertyInformationCreationStrategy {

    boolean isApplicable(PropertyInformationCreationDto propertyInformationCreation);

    PropertyInformation map(PropertyInformationCreationDto propertyInformationCreation);

}
