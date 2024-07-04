package com.soitio.widgets.startup.runtime.config;

import com.soitio.widgets.startup.runtime.domain.IWidgetDefinition;
import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

import java.util.List;

@StaticInitSafe
@ConfigMapping(prefix = "widgets")
public interface WidgetsConfiguration {

    List<IWidgetDefinition> wigetDefinitions();

}
