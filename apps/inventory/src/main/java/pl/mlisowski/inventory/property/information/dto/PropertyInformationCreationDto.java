package pl.mlisowski.inventory.property.information.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.mlisowski.inventory.property.information.PropertyType;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class PropertyInformationCreationDto {

    private PropertyType propertyType;

}
