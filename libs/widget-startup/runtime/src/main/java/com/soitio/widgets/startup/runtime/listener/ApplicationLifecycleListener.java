package com.soitio.widgets.startup.runtime.listener;

import com.soitio.widgets.startup.runtime.client.DashboardClient;
import com.soitio.widgets.startup.runtime.config.WidgetsConfiguration;
import com.soitio.widgets.startup.runtime.domain.IWidgetDefinition;
import com.soitio.widgets.common.domain.WidgetDefinition;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ApplicationLifecycleListener {

    private final DashboardClient dashboardClient;

    private final WidgetsConfiguration widgetsConfiguration;

    public ApplicationLifecycleListener(@RestClient DashboardClient dashboardClient,
                                        WidgetsConfiguration widgetsConfiguration) {
        this.dashboardClient = dashboardClient;
        this.widgetsConfiguration = widgetsConfiguration;
    }

    @Startup
    void init() {
        dashboardClient.uploadWidgetDefinitions(widgetsConfiguration.wigetDefinitions().stream()
                .map(this::to)
                .toList());
    }

    private WidgetDefinition to(IWidgetDefinition widgetDefinition) {
        return WidgetDefinition.builder()
                .build();
    }

}
