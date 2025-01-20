package com.soitio.dashboard.application;

import com.soitio.dashboard.common.OrgMongoRepository;
import com.soitio.dashboard.common.Position;
import com.soitio.dashboard.domain.Dashboard;
import com.soitio.dashboard.domain.DashboardType;
import com.soitio.dashboard.domain.dto.DashboardCreationDto;
import com.soitio.dashboard.domain.dto.DashboardDto;
import com.soitio.dashboard.domain.dto.DashboardForSelectionDto;
import com.soitio.dashboard.widget.application.WidgetRepository;
import com.soitio.dashboard.widget.domain.Widget;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@ApplicationScoped
@RequiredArgsConstructor
public class DashboardRepository implements OrgMongoRepository<Dashboard> {

    private final WidgetRepository widgetRepository;

    public List<DashboardDto> getDashboards(String orgId) {
        return findAllByOrgId(orgId).stream()
                .map(this::to)
                .toList();
    }

    public DashboardDto getDashboard(String dashboardId, String orgId) {
        return findByIdAndOrgIdOptional(dashboardId, orgId)
                .map(this::to)
                .orElseThrow(); // TODO: Throw some specific exception with reason what went wrong
    }

    /* Finds default widget for defined type */
    public DashboardDto getDashboardForType(String type, String orgId) {
        return findByTypeAndOrgIdOptional(DashboardType.valueOf(type), orgId)
                .map(this::to)
                .orElseThrow(); // TODO: Throw some specific exception with reason what went wrong
    }

    private Optional<Dashboard> findByTypeAndOrgIdOptional(DashboardType type, String orgId) {
        return find("type = ?1 and orgId = ?2", type, orgId).firstResultOptional();
    }

    public void createDashboard(DashboardCreationDto dashboardCreation, String orgId) {
        Optional<Dashboard> defaultDashboard = findByTypeAndOrgIdOptional(dashboardCreation.getType(), orgId);

        persist(Dashboard.builder()
                .name(dashboardCreation.getName())
                .type(dashboardCreation.getType())
                .defaultForType(defaultDashboard.isEmpty())
                .availableWidgetPosition(new Position(0, 0))
                .orgId(orgId)
                .build());
    }

    public void setDashboardDefault(String dashboardId, String orgId) {
        update("defaultForType", true).where("_id = ?1 and orgId = ?2",
                new ObjectId(dashboardId), orgId);
    }

    private DashboardDto to(Dashboard dashboard) {
        return DashboardDto.builder()
                .id(dashboard.getId().toString())
                .name(dashboard.getName())
                .type(dashboard.getType())
                .defaultForType(dashboard.isDefaultForType())
                .widgets(widgetRepository.getWidgetsByIds(dashboard.getWidgets(), dashboard.getOrgId()))
                .build();
    }

    public void createWidgetInDashboard(WidgetCreationDto widgetCreation, String dashboardId, String orgId) {
        Dashboard dashboard = findById(new ObjectId(dashboardId));
        Position widgetPosition = dashboard.getAvailableWidgetPosition();
        dashboard.addWidget(widgetRepository.createWidget(widgetCreation, widgetPosition, orgId));
        dashboard.setAvailableWidgetPosition(determineNextPosition(widgetPosition));
        update(dashboard);
    }

    private Position determineNextPosition(Position position) {
        if (position.x() == 2) return new Position(0, position.y() + 1);
        return new Position(position.x() + 1, position.y());
    }

    public List<DashboardForSelectionDto> getDashboardForSelection(String type, String orgId) {
        return find("type = ?1 and orgId = ?2", DashboardType.valueOf(type), orgId).stream()
                .map(this::toSelection)
                .toList();
    }

    private DashboardForSelectionDto toSelection(Dashboard dashboard) {
        return DashboardForSelectionDto.builder()
                .id(dashboard.getId().toString())
                .name(dashboard.getName())
                .defaultForType(dashboard.isDefaultForType())
                .build();
    }

    public void deleteWidgetFromDashboard(String widgetId, String dashboardId, String orgId) {
        Dashboard dashboard = findByIdAndOrgId(dashboardId, orgId);
        ObjectId widgetObjId = new ObjectId(widgetId);
        dashboard.getWidgets().remove(widgetObjId);

        Position widgetToRemovePosition = widgetRepository.findByIdAndOrgId(widgetObjId, orgId).getPosition();
        widgetRepository.deleteByIdAndOrgId(widgetObjId, orgId);

        List<Widget> widgets = widgetRepository.getPlainWidgetsByIds(dashboard.getWidgets(), orgId);
        Position newAvailablePosition = recalculateWidgetPositions(widgetToRemovePosition, widgets);

        dashboard.setAvailableWidgetPosition(newAvailablePosition);

        update(dashboard);
        widgetRepository.update(widgets);
    }

    public Position recalculateWidgetPositions(Position position, List<Widget> widgets) {
        return widgets.stream()
                .filter(w -> applicableForPositionUpdate(position, w.getPosition()))
                .map(this::determineNewPositionAfterRemoval)
                .reduce(this::determineLastPosition)
                .map(this::determineNextPosition)
                .orElse(new Position(0, 0));
    }

    private boolean applicableForPositionUpdate(Position removedItemPosition, Position itemToUpdatePosition) {
        return itemToUpdatePosition.y() > removedItemPosition.y()
                || (itemToUpdatePosition.y() == removedItemPosition.y()
                && itemToUpdatePosition.x() > removedItemPosition.x());
    }

    private Position determineNewPositionAfterRemoval(Widget widget) {
        Position position = widget.getPosition();
        Position newPosition;
        if (position.x() == 0) newPosition = new Position(2, position.y() - 1);
        else newPosition = new Position(position.x() - 1, position.y());
        widget.setPosition(newPosition);
        return newPosition;
    }

    private Position determineLastPosition(Position p1, Position p2) {
        if (p1.y() > p2.y()) return p1;
        if (p2.y() > p1.y()) return p2;
        return p1.x() > p2.x() ? p1 : p2;
    }
}
