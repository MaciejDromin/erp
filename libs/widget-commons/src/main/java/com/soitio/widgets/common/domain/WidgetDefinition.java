package com.soitio.widgets.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class WidgetDefinition {

    private String name;
    private String datasource;
    private Set<Filter> availableFilters;
    private WidgetDomain widgetDomain;
    private String version;
    private String uniqueCode;

}
