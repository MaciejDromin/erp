package com.soitio.dashboard.widget.domain;

import com.soitio.dashboard.common.Position;
import com.soitio.widgets.common.domain.WidgetDomain;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.HashMap;
import java.util.Map;
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
@MongoEntity(collection = "Widget")
@EqualsAndHashCode(of = "id")
public class Widget {

    private ObjectId id;
    private String name;

    @Builder.Default
    private Map<String, Object> filters = new HashMap<>();

    private WidgetType widgetType;
    private WidgetDomain widgetDomain;
    private String datasource;
    private Position position;
    private ObjectId widgetDefinitionId;

}
