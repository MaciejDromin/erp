package com.soitio.widgets.startup.runtime.config;

import com.soitio.widgets.startup.runtime.domain.IWidgetDefinition;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

import java.util.List;

@StaticInitSafe
@ConfigMapping(prefix = "widgets")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface WidgetsConfiguration {

    /**
     * Widget Definitions
     * */
    List<IWidgetDefinition> widgetDefinitions();

}
