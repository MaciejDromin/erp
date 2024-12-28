package com.soitio.api.gateway.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@MongoEntity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ConfigResource {

    private ObjectId id;
    private String key;
    private ValueWrapper value;

}
