package com.soitio.inventory.contractor.domain;


import com.soitio.inventory.commons.BaseEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.soitio.inventory.contractor.contact.domain.ContactInformation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "Contractor")
@ToString
public class Contractor extends BaseEntity {

    private String name;
    private ContactInformation contactInformation;

}
