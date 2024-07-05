package com.soitio.widgets.startup.runtime.domain;

import com.soitio.widgets.common.domain.WidgetDomain;
import java.util.Set;

public interface IWidgetDefinition {

    String name();
    String datasource();
    Set<IFilter> availableFilters();
    WidgetDomain widgetDomain();
    String version();
    String uniqueCode();

}
