package com.soitio.dashboard.widget.definition.codec.strategy;

import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MapperStrategyProvider {

    private final List<MapperStrategy<?>> mapperStrategies;

    public MapperStrategyProvider(@All List<MapperStrategy<?>> mapperStrategies) {
        this.mapperStrategies = mapperStrategies;
    }

    public MapperStrategy<?> getMapper(Object o) {
        return mapperStrategies.stream()
                .filter(m -> m.isApplicable(o))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find mapper for class '%s'"
                        .formatted(o.getClass().getSimpleName())));
    }
}
