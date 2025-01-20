package com.soitio.dashboard.domain;

import com.soitio.dashboard.common.BaseEntity;
import com.soitio.dashboard.common.Position;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MongoEntity(collection = "Dashboard")
public class Dashboard extends BaseEntity {

    private String name;

    @Builder.Default
    private Set<ObjectId> widgets = new HashSet<>();

    private DashboardType type;

    @Builder.Default
    private boolean defaultForType = false;

    private Position availableWidgetPosition;

    public void addWidget(ObjectId id) {
        this.widgets.add(id);
    }
}
