package com.soitio.inventory.part.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "Part")
@ToString
@EqualsAndHashCode(of = "id")
public class Part {

    private ObjectId id;
    private String name;
    private String partNumber;
    private ObjectId manufacturerId;

}
