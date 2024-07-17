package pl.mlisowski.inventory.property.information.strategy.impl;

import jakarta.enterprise.context.ApplicationScoped;
import pl.mlisowski.inventory.property.information.LandInformation;
import pl.mlisowski.inventory.property.information.PropertyInformation;
import pl.mlisowski.inventory.property.information.dto.LandInformationCreationDto;
import pl.mlisowski.inventory.property.information.dto.PropertyInformationCreationDto;
import pl.mlisowski.inventory.property.information.strategy.PropertyInformationCreationStrategy;

@ApplicationScoped
public class LandInformationCreationStrategy implements PropertyInformationCreationStrategy {

    @Override
    public boolean isApplicable(PropertyInformationCreationDto propertyInformationCreation) {
        return propertyInformationCreation instanceof LandInformationCreationDto;
    }

    @Override
    public PropertyInformation map(PropertyInformationCreationDto propertyInformationCreation) {
        LandInformationCreationDto landInformationCreation = (LandInformationCreationDto) propertyInformationCreation;
        return LandInformation.builder()
                .propertyType(landInformationCreation.getPropertyType())
                .landArea(landInformationCreation.getLandArea())
                .landClassification(landInformationCreation.getLandClassification())
                .build();
    }

}
