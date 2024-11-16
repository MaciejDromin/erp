package com.soitio.inventory.property.information.strategy;

import com.soitio.inventory.property.information.PropertyType;
import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import com.soitio.inventory.property.information.PropertyInformation;
import com.soitio.inventory.property.information.dto.PropertyInformationCreationDto;
import java.util.List;

@ApplicationScoped
public class PropertyInformationProvider {

    private final List<PropertyInformationStrategy<?, ?>> strategies;

    public PropertyInformationProvider(@All List<PropertyInformationStrategy<?, ?>> strategies) {
        this.strategies = strategies;
    }

    public PropertyInformationStrategy<?, ?> get(final PropertyType type) {
        return strategies.stream()
                .filter(strategy -> strategy.isApplicable(type))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find implementation"));
    }

}
