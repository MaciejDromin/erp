package com.soitio.widgets.startup.runtime.converters;

import static io.quarkus.runtime.configuration.ConverterSupport.DEFAULT_SMALLRYE_CONVERTER_PRIORITY;

import com.soitio.widgets.common.domain.data.AnyPossibleValueWrapper;
import jakarta.annotation.Priority;
import org.eclipse.microprofile.config.spi.Converter;
import java.util.Optional;
import java.util.Set;

@Priority(DEFAULT_SMALLRYE_CONVERTER_PRIORITY)
public class AnyPossibleValueConverter implements Converter<AnyPossibleValueWrapper> {

    private static final Set<ValueOfBasedConverter<?>> CONVERTERS = Set.of(
            ValueOfBasedConverter.getIfEligible(Integer.class),
            ValueOfBasedConverter.getIfEligible(Double.class)
    );

    @Override
    public AnyPossibleValueWrapper convert(String s) throws IllegalArgumentException, NullPointerException {
        return tryToMapInto(s).orElse(new AnyPossibleValueWrapper(s));
    }

    private Optional<AnyPossibleValueWrapper> tryToMapInto(String s) {
        for (var converter : CONVERTERS) {
            try {
                return Optional.of(new AnyPossibleValueWrapper(converter.fromString(s)));
            } catch (IllegalArgumentException ignored) {}
        }
        return Optional.empty();
    }
}
