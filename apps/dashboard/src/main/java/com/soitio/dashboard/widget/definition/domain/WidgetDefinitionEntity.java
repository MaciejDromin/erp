package com.soitio.dashboard.widget.definition.domain;

import com.soitio.dashboard.common.WidgetDomain;
import com.soitio.widgets.common.domain.Filter;
import io.quarkus.mongodb.panache.common.MongoEntity;
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
@MongoEntity(collection = "WidgetDefinition")
@EqualsAndHashCode(of = "id")
public class WidgetDefinitionEntity {

    private ObjectId id;
    private String name;
    private String datasource;
    private Set<Filter> availableFilters;
    private WidgetDomain widgetDomain;

}
