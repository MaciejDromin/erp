package com.soitio.dashboard.widget.application;

import com.soitio.dashboard.common.Position;
import com.soitio.dashboard.widget.domain.Widget;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import com.soitio.dashboard.widget.domain.dto.WidgetDto;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Set;
import org.bson.types.ObjectId;

@ApplicationScoped
public class WidgetRepository implements PanacheMongoRepository<Widget> {


    public ObjectId createWidget(WidgetCreationDto widgetCreation, Position position) {
        Widget widget = to(widgetCreation, position);
        persist(widget);
        return widget.getId();
    }

    public List<WidgetDto> getWidgetsByIds(Set<ObjectId> widgets) {
        return list("_id in ?1", widgets).stream()
                .map(this::from)
                .toList();
    }

    public List<Widget> getPlainWidgetsByIds(Set<ObjectId> widgets) {
        return list("_id in ?1", widgets).stream()
                .toList();
    }

    private Widget to(WidgetCreationDto widgetCreation, Position position) {
        return Widget.builder()
                .name(widgetCreation.getName())
                .filters(widgetCreation.getFilters())
                .widgetType(widgetCreation.getWidgetType())
                .widgetDomain(widgetCreation.getWidgetDomain())
                .datasource(widgetCreation.getDatasource())
                .position(position)
                .widgetDefinitionId(widgetCreation.getWidgetDefinitionId())
                .build();
    }

    private WidgetDto from(Widget widget) {
        return WidgetDto.builder()
                .id(widget.getId().toString())
                .name(widget.getName())
                .filters(widget.getFilters())
                .widgetType(widget.getWidgetType())
                .widgetDomain(widget.getWidgetDomain())
                .datasource(widget.getDatasource())
                .position(widget.getPosition())
                .build();
    }

}
