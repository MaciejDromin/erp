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
import java.util.Set;

@MongoEntity(database = "auth")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserResource {

    private ObjectId id;
    private String username;
    private String email;
    private String password;
//    private Set<Role> roles;
    private String currentOrgId;
    private Set<String> orgs;

}
