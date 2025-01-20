package com.soitio.dashboard.widget.definition.domain;

import com.soitio.dashboard.common.BaseEntity;
import com.soitio.widgets.common.domain.Filter;
import com.soitio.widgets.common.domain.WidgetDomain;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MongoEntity(collection = "WidgetDefinition")
public class WidgetDefinitionEntity extends BaseEntity {

    private String name;
    private String datasource;
    private List<Filter> availableFilters;
    private WidgetDomain widgetDomain;
    private String version;
    private String uniqueCode;

}
