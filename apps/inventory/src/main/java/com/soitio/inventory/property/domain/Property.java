package com.soitio.inventory.property.domain;

import com.soitio.commons.dependency.Dependencies;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import com.soitio.inventory.property.information.PropertyInformation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "Property")
@ToString
@EqualsAndHashCode(of = "id")
@Dependencies(dependent = "Property", dependencies = {"inventory.address"})
public class Property {

    private ObjectId id;
    private String name;
    private String uniqueIdentifier;
    private ObjectId addressId;
    private String landRegister;
    private PropertyInformation propertyInformation;

}
