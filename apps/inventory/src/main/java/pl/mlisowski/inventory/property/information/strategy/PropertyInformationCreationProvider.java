package pl.mlisowski.inventory.property.information.strategy;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.inventory.property.information.PropertyInformation;
import pl.mlisowski.inventory.property.information.dto.PropertyInformationCreationDto;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class PropertyInformationCreationProvider {

    private final List<PropertyInformationCreationStrategy> strategies;

    public PropertyInformation map(PropertyInformationCreationDto informationCreation) {
        return strategies.stream()
                .filter(strategy -> strategy.isApplicable(informationCreation))
                .findFirst().map(strategy -> strategy.map(informationCreation))
                .orElseThrow(() -> new IllegalStateException("Could not find implementation"));
    }

}
