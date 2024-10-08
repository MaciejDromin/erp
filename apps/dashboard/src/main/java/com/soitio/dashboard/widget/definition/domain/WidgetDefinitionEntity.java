package com.soitio.dashboard.widget.definition.domain;

import com.soitio.widgets.common.domain.Filter;
import com.soitio.widgets.common.domain.WidgetDomain;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;
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
    private List<Filter> availableFilters;
    private WidgetDomain widgetDomain;
    private String version;
    private String uniqueCode;

}
