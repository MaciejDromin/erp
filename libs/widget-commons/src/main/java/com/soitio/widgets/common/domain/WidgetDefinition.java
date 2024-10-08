package com.soitio.widgets.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class WidgetDefinition {

    private String name;
    private String datasource;
    private List<Filter> availableFilters;
    private WidgetDomain widgetDomain;
    private String version;
    private String uniqueCode;

}
