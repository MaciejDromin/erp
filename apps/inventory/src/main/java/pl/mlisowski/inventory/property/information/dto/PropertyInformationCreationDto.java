package pl.mlisowski.inventory.property.information.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.mlisowski.inventory.property.information.PropertyType;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "propertyType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LandInformationCreationDto.class, name = "LAND")
})
public abstract class PropertyInformationCreationDto {

    private PropertyType propertyType;

}
