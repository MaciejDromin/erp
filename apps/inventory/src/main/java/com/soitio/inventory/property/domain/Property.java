package com.soitio.inventory.property.domain;

import com.soitio.commons.dependency.Dependencies;
import com.soitio.inventory.commons.BaseEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Dependencies(dependent = "Property", dependencies = {"inventory.address"})
public class Property extends BaseEntity {

    private String name;
    private String uniqueIdentifier;
    private ObjectId addressId;
    private String landRegister;
    private PropertyInformation propertyInformation;

}
