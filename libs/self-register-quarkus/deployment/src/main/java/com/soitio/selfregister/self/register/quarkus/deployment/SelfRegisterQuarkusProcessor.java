package com.soitio.selfregister.self.register.quarkus.deployment;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.processor.DotNames;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;

import java.lang.reflect.Modifier;
import java.util.List;

class SelfRegisterQuarkusProcessor {

    private static final String FEATURE = "self-register-quarkus";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    IndexDependencyBuildItem indexExternalDependency() {
        return new IndexDependencyBuildItem("com.soitio.selfregister", "self-register-quarkus");
    }

    @BuildStep
    void declareWidgetStartupsAsBean(CombinedIndexBuildItem index,
                                     BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        List<String> allBeans = index.getIndex().getKnownClasses().stream()
                .filter(ci -> !Modifier.isAbstract(ci.flags()))
                .map(ci -> ci.name().toString())
                .filter(c -> c.startsWith("com.soitio.selfregister.self.register"))
                .filter(c -> !c.startsWith("com.soitio.selfregister.self.register.quarkus.runtime.domain"))
                .toList();

        additionalBeans.produce(new AdditionalBeanBuildItem.Builder()
                .addBeanClasses(allBeans)
                .setUnremovable()
                .setDefaultScope(DotNames.APPLICATION_SCOPED)
                .build());
    }

}
