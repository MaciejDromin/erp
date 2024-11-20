package com.soitio.inventory.property.information.strategy.impl;

import com.soitio.commons.models.commons.MergePatch;
import com.soitio.inventory.property.area.Area;
import com.soitio.inventory.property.area.AreaUnit;
import com.soitio.inventory.property.information.LandClassification;
import com.soitio.inventory.property.information.PropertyInformation;
import com.soitio.inventory.property.information.PropertyType;
import com.soitio.inventory.property.information.dto.LandPropertyInformationDto;
import com.soitio.inventory.property.information.dto.PropertyInformationCreationDto;
import jakarta.enterprise.context.ApplicationScoped;
import com.soitio.inventory.property.information.LandInformation;
import com.soitio.inventory.property.information.dto.LandInformationCreationDto;
import com.soitio.inventory.property.information.strategy.PropertyInformationStrategy;

@ApplicationScoped
public class LandInformationStrategy implements PropertyInformationStrategy<LandPropertyInformationDto,
                                                                            LandInformation> {

    @Override
    public boolean isApplicable(PropertyType type) {
        return type == PropertyType.LAND;
    }

    @Override
    public LandInformation create(PropertyInformationCreationDto propertyCreation) {
        LandInformationCreationDto landInformationCreation = (LandInformationCreationDto) propertyCreation;
        return LandInformation.builder()
                .propertyType(landInformationCreation.getPropertyType())
                .landArea(landInformationCreation.getLandArea())
                .landClassification(landInformationCreation.getLandClassification())
                .build();
    }

    @Override
    public PropertyInformation jsonToEntity(MergePatch value) {
        var fields = value.getObjectValue();
        var area = fields.get("landArea").getObjectValue();
        return LandInformation.builder()
                .propertyType(PropertyType.valueOf(fields.get("propertyType").getStrValue()))
                .landClassification(LandClassification.valueOf(fields.get("landClassification").getStrValue()))
                .landArea(new Area(area.get("area").getIntValue(), AreaUnit.getEnum(area.get("unit").getStrValue())))
                .build();
    }

    @Override
    public LandPropertyInformationDto from(PropertyInformation propertyInformation) {
        LandInformation information = (LandInformation) propertyInformation;
        return LandPropertyInformationDto.builder()
                .propertyType(information.getPropertyType())
                .landClassification(information.getLandClassification())
                .landArea(information.getLandArea())
                .build();
    }

}
