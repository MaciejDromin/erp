package com.soitio.dashboard.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class Dashboard {

    private ObjectId id;
    private String name;
    private List<Object> widgets;
    private DashboardType type;

}
