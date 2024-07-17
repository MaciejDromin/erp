package pl.mlisowski.inventory.property.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import pl.mlisowski.inventory.property.information.dto.PropertyInformationCreationDto;

@Builder
@AllArgsConstructor
@Getter
public class PropertyCreationDto {

    private String name;
    private String uniqueIdentifier;
    private ObjectId addressId;
    private PropertyInformationCreationDto propertyInformation;

}
