package com.soitio.api.gateway.codec.strategy;

import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CodecStrategyProvider {

    private final List<CodecStrategy<?>> codecStrategies;

    public CodecStrategyProvider(@All List<CodecStrategy<?>> mapperStrategies) {
        this.codecStrategies = mapperStrategies;
    }

    public CodecStrategy<?> getMapper(Object o) {
        return codecStrategies.stream()
                .filter(m -> m.isApplicable(o))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find codec for class '%s'"
                        .formatted(o.getClass().getSimpleName())));
    }
}
