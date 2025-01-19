package com.soitio.inventory.contractor.domain;


import com.soitio.inventory.commons.BaseEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.soitio.inventory.contractor.contact.domain.ContactInformation;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MongoEntity(collection = "Contractor")
@ToString
public class Contractor extends BaseEntity {

    private String name;
    private ContactInformation contactInformation;

}
