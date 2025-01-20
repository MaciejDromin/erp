package com.soitio.dashboard.widget.application;

import com.soitio.dashboard.common.OrgMongoRepository;
import com.soitio.dashboard.common.Position;
import com.soitio.dashboard.widget.domain.Widget;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import com.soitio.dashboard.widget.domain.dto.WidgetDto;
import com.soitio.dashboard.widget.domain.dto.WidgetPositionUpdateDto;
import com.soitio.dashboard.widget.domain.dto.WidgetUpdateDto;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

@ApplicationScoped
public class WidgetRepository implements OrgMongoRepository<Widget> {

    public ObjectId createWidget(WidgetCreationDto widgetCreation, Position position, String orgId) {
        Widget widget = to(widgetCreation, position, orgId);
        persist(widget);
        return widget.getId();
    }

    public List<WidgetDto> getWidgetsByIds(Set<ObjectId> widgets, String orgId) {
        return listByIdsAndOrgId(widgets, orgId).stream()
                .map(this::from)
                .toList();
    }

    public List<Widget> getPlainWidgetsByIds(Set<ObjectId> widgets, String orgId) {
        return listByIdsAndOrgId(widgets, orgId);
    }

    private Widget to(WidgetCreationDto widgetCreation, Position position, String orgId) {
        return Widget.builder()
                .name(widgetCreation.getName())
                .filters(widgetCreation.getFilters())
                .widgetType(widgetCreation.getWidgetType())
                .widgetDomain(widgetCreation.getWidgetDomain())
                .datasource(widgetCreation.getDatasource())
                .position(position)
                .widgetDefinitionId(widgetCreation.getWidgetDefinitionId())
                .orgId(orgId)
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

    public void updateWidgetFilters(String widgetId, Map<String, Object> filters, String orgId) {
        Widget widget = findByIdAndOrgId(widgetId, orgId);
        widget.setFilters(filters);
        update(widget);
    }

    public void updatePositions(List<WidgetPositionUpdateDto> toUpdate, String orgId) {
        Map<ObjectId, WidgetPositionUpdateDto> map = toUpdate.stream()
                .collect(Collectors.toMap(w -> new ObjectId(w.getId()), w -> w));
        List<Widget> widgets = listByIdsAndOrgId(map.keySet(), orgId);
        widgets.forEach(w -> {
            w.setPosition(map.get(w.getId()).getPosition());
            update(w);
        });
    }

    public void updateWidget(String widgetId, WidgetUpdateDto update, String orgId) {
        Widget widget = findByIdAndOrgId(widgetId, orgId);
        widget.setName(update.getName());
        widget.setWidgetType(update.getType());
        update(widget);
    }
}
