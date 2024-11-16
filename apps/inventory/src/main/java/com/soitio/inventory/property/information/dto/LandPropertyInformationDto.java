package com.soitio.inventory.property.information.dto;

import com.soitio.inventory.property.area.Area;
import com.soitio.inventory.property.information.LandClassification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LandPropertyInformationDto extends PropertyInformationDto {

    private LandClassification landClassification;
    private Area landArea;

}
