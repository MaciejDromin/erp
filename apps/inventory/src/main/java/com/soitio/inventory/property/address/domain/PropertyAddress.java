package com.soitio.inventory.property.address.domain;

import com.soitio.inventory.commons.BaseEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MongoEntity(collection = "PropertyAddress")
public class PropertyAddress extends BaseEntity {

    private String addressLine;
    private String city;
    private String country;
    private String province;
    private String postalCode;

}
