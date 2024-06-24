package com.soitio.dashboard.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "Dashboard")
@EqualsAndHashCode(of = "id")
public class Dashboard {

    private ObjectId id;
    private String name;

    @Builder.Default
    private Set<ObjectId> widgets = new HashSet<>();

    private DashboardType type;

    @Builder.Default
    private boolean defaultForType = false;

}
