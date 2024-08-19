package com.soitio.widgets.startup.runtime.listener;

import com.soitio.widgets.common.domain.Filter;
import com.soitio.widgets.startup.runtime.client.DashboardClient;
import com.soitio.widgets.startup.runtime.config.WidgetsConfiguration;
import com.soitio.widgets.startup.runtime.domain.IFilter;
import com.soitio.widgets.startup.runtime.domain.IWidgetDefinition;
import com.soitio.widgets.common.domain.WidgetDefinition;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.Optional;
import java.util.function.Supplier;

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
        dashboardClient.uploadWidgetDefinitions(widgetsConfiguration.widgetDefinitions().stream()
                .map(this::to)
                .toList());
    }

    private WidgetDefinition to(IWidgetDefinition widgetDefinition) {
        return WidgetDefinition.builder()
                .name(widgetDefinition.name())
                .datasource(widgetDefinition.datasource())
                .availableFilters(widgetDefinition.availableFilters().stream()
                        .map(this::toFilter)
                        .toList())
                .widgetDomain(widgetDefinition.widgetDomain())
                .version(widgetDefinition.version())
                .uniqueCode(widgetDefinition.uniqueCode())
                .build();
    }

    private Filter toFilter(IFilter filter) {
        return Filter.builder()
                .name(filter.name())
                .dependsOn(getOrNull(filter::dependsOn))
                .min(filter.min())
                .max(filter.max())
                .options(getOrNull(filter::options))
                .mandatory(filter.mandatory())
                .datasource(getOrNull(filter::datasource))
                .filterType(filter.filterType())
                .build();
    }

    private <T> T getOrNull(Supplier<Optional<T>> method) {
        return method.get().orElse(null);
    }

}
