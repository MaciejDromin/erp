package com.soitio.widgets.startup.deployment;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.processor.DotNames;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import java.lang.reflect.Modifier;
import java.util.List;

class WidgetStartupProcessor {

    private static final String FEATURE = "widget-startup";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    IndexDependencyBuildItem indexExternalDependency() {
        return new IndexDependencyBuildItem("com.soitio.widgets", "widget-startup");
    }

    @BuildStep
    void declareWidgetStartupsAsBean(CombinedIndexBuildItem index,
                                          BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        List<String> allBeans = index.getIndex().getKnownClasses().stream()
                .filter(ci -> !Modifier.isAbstract(ci.flags()))
                .map(ci -> ci.name().toString())
                .filter(c -> c.startsWith("com.soitio.widgets.startup"))
                .toList();

        additionalBeans.produce(new AdditionalBeanBuildItem.Builder()
                .addBeanClasses(allBeans)
                .setUnremovable()
                .setDefaultScope(DotNames.APPLICATION_SCOPED)
                .build());
    }

}
