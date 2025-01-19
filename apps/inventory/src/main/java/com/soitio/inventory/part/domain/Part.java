package com.soitio.inventory.part.domain;

import com.soitio.commons.dependency.Dependencies;
import com.soitio.inventory.commons.BaseEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MongoEntity(collection = "Part")
@ToString
@Dependencies(dependent = "Part", dependencies = {"inventory.contractor"})
public class Part extends BaseEntity {

    private String name;
    private String partNumber;
    private ObjectId manufacturerId;

}
