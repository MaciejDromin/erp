package com.soitio.widgets.startup.deployment;

import com.soitio.widgets.startup.runtime.client.DashboardClient;
import com.soitio.widgets.startup.runtime.config.WidgetsConfiguration;
import com.soitio.widgets.startup.runtime.listener.ApplicationLifecycleListener;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.AdditionalIndexedClassesBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class WidgetStartupProcessor {

    private static final String FEATURE = "widget-startup";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalIndexedClassesBuildItem configurationBuildItem() {
        return new AdditionalIndexedClassesBuildItem(WidgetsConfiguration.class.getName());
    }

    @BuildStep
    AdditionalIndexedClassesBuildItem restClient() {
        return new AdditionalIndexedClassesBuildItem(DashboardClient.class.getName());
    }

    @BuildStep
    AdditionalIndexedClassesBuildItem applicationStarter() {
        return new AdditionalIndexedClassesBuildItem(ApplicationLifecycleListener.class.getName());
    }


}
