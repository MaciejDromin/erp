package com.soitio.widgets.startup.runtime.domain;

import com.soitio.widgets.common.domain.WidgetDomain;
import java.util.Set;

public interface IWidgetDefinition {

    /**
     * Widget name
     * */
    String name();

    /**
     * Widget datasource
     * */
    String datasource();

    /**
     * Widget filters
     * */
    Set<IFilter> availableFilters();

    /**
     * Widget domain
     * */
    WidgetDomain widgetDomain();

    /**
     * Widget version
     * */
    String version();

    /**
     * Widget unique code
     * */
    String uniqueCode();

}
