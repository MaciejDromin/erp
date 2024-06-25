package com.soitio.dashboard.widget.application;

import com.soitio.dashboard.widget.domain.Widget;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

@ApplicationScoped
public class WidgetRepository implements PanacheMongoRepository<Widget> {

    public Set<ObjectId> createWidgets(List<WidgetCreationDto> widgets) {
        return widgets.stream()
                .map(this::createWidget)
                .collect(Collectors.toSet());
    }

    public ObjectId createWidget(WidgetCreationDto widgetCreation) {
        Widget widget = to(widgetCreation);
        persist(widget);
        return widget.getId();
    }

    private Widget to(WidgetCreationDto widgetCreation) {
        return Widget.builder()
                .name(widgetCreation.getName())
                .filters(widgetCreation.getFilters())
                .widgetType(widgetCreation.getWidgetType())
                .widgetDomain(widgetCreation.getWidgetDomain())
                .datasource(widgetCreation.getDatasource())
                //  .position(determinePosition()) // TODO: Determine Position
                .build();
    }

}
