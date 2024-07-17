package pl.mlisowski.inventory.property.information.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.mlisowski.inventory.property.area.Area;
import pl.mlisowski.inventory.property.information.LandClassification;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LandInformationCreationDto extends PropertyInformationCreationDto {

    private LandClassification landClassification;
    private Area landArea;

}
