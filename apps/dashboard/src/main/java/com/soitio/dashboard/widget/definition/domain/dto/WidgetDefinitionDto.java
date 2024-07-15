package com.soitio.dashboard.widget.definition.domain.dto;

import com.soitio.dashboard.widget.definition.domain.WidgetDefinitionEntity;
import com.soitio.widgets.common.domain.Filter;
import com.soitio.widgets.common.domain.WidgetDomain;
import io.quarkus.mongodb.panache.common.ProjectionFor;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ProjectionFor(WidgetDefinitionEntity.class)
public class WidgetDefinitionDto {

    private ObjectId id;
    private String name;
    private String datasource;
    private Set<Filter> availableFilters;
    private WidgetDomain widgetDomain;
    private String version;
    private String uniqueCode;

}
