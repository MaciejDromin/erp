package pl.mlisowski.inventory.property.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import pl.mlisowski.inventory.property.information.PropertyInformation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "Property")
public class Property {

    private ObjectId id;
    private String name;
    private String uniqueIdentifier;
    private ObjectId addressId;
    private PropertyInformation propertyInformation;

}
