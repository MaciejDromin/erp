package com.soitio.api.gateway.auth.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@MongoEntity(database = "auth")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrgResource {

    private ObjectId id;
    private String orgId;
    private String name;
    //    private String creator;
    //    private OrgStatus status; -- PAYING, DEACTIVATED, TRIAL...

}
