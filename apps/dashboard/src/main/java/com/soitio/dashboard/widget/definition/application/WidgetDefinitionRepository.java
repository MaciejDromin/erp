package com.soitio.dashboard.widget.definition.application;

import com.soitio.dashboard.widget.definition.domain.WidgetDefinition;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WidgetDefinitionRepository implements PanacheMongoRepository<WidgetDefinition> {
}
