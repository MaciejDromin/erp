package com.soitio.widget.startup.runtime.config;

import com.soitio.widget.startup.runtime.domain.IWidgetDefinition;
import io.smallrye.config.ConfigMapping;

import java.util.List;

@ConfigMapping(prefix = "widgets")
public interface WidgetsConfiguration {

    List<IWidgetDefinition> wigetDefinitions();

}
