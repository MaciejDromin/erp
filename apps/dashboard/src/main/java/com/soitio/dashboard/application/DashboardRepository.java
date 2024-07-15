package com.soitio.dashboard.application;

import com.soitio.dashboard.common.Position;
import com.soitio.dashboard.domain.Dashboard;
import com.soitio.dashboard.domain.DashboardType;
import com.soitio.dashboard.domain.dto.DashboardCreationDto;
import com.soitio.dashboard.domain.dto.DashboardDto;
import com.soitio.dashboard.domain.dto.DashboardForSelectionDto;
import com.soitio.dashboard.widget.application.WidgetRepository;
import com.soitio.dashboard.widget.domain.Widget;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@ApplicationScoped
@RequiredArgsConstructor
public class DashboardRepository implements PanacheMongoRepository<Dashboard> {

    private final WidgetRepository widgetRepository;

    public List<DashboardDto> getDashboards() {
        return findAll().stream()
                .map(this::to)
                .toList();
    }

    public DashboardDto getDashboard(String dashboardId) {
        return findByIdOptional(new ObjectId(dashboardId))
                .map(this::to)
                .orElseThrow(); // TODO: Throw some specific exception with reason what went wrong
    }

    /* Finds default widget for defined type */
    public DashboardDto getDashboardForType(String type) {
        return find("type = ?1", DashboardType.valueOf(type))
                .firstResultOptional()
                .map(this::to)
                .orElseThrow(); // TODO: Throw some specific exception with reason what went wrong
    }

    public void createDashboard(DashboardCreationDto dashboardCreation) {
        Optional<Dashboard> defaultDashboard = find("type = ?1", dashboardCreation.getType()).firstResultOptional();

        persist(Dashboard.builder()
                .name(dashboardCreation.getName())
                .type(dashboardCreation.getType())
                .defaultForType(defaultDashboard.isEmpty())
                .availableWidgetPosition(new Position(0, 0))
                .build());
    }

    public void setDashboardDefault(String dashboardId) {
        update("defaultForType", true).where("_id", new ObjectId(dashboardId));
    }

    private DashboardDto to(Dashboard dashboard) {
        return DashboardDto.builder()
                .id(dashboard.getId().toString())
                .name(dashboard.getName())
                .type(dashboard.getType())
                .defaultForType(dashboard.isDefaultForType())
                .widgets(widgetRepository.getWidgetsByIds(dashboard.getWidgets()))
                .build();
    }

    public void createWidgetInDashboard(WidgetCreationDto widgetCreation, String dashboardId) {
        Dashboard dashboard = findById(new ObjectId(dashboardId));
        Position widgetPosition = dashboard.getAvailableWidgetPosition();
        dashboard.addWidget(widgetRepository.createWidget(widgetCreation, widgetPosition));
        dashboard.setAvailableWidgetPosition(determineNextPosition(widgetPosition));
        update(dashboard);
    }

    private Position determineNextPosition(Position position) {
        if (position.x() == 2) return new Position(0, position.y() + 1);
        return new Position(position.x() + 1, position.y());
    }

    public List<DashboardForSelectionDto> getDashboardForSelection(String type) {
        return find("type = ?1", DashboardType.valueOf(type)).stream()
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

    public void deleteWidgetFromDashboard(String widgetId, String dashboardId) {
        Dashboard dashboard = findById(new ObjectId(dashboardId));
        ObjectId widgetObjId = new ObjectId(widgetId);
        dashboard.getWidgets().remove(widgetObjId);

        Position widgetToRemovePosition = widgetRepository.findById(widgetObjId).getPosition();
        widgetRepository.deleteById(widgetObjId);

        List<Widget> widgets = widgetRepository.getPlainWidgetsByIds(dashboard.getWidgets());
        Position newAvailablePosition = recalculateWidgetPositions(widgetToRemovePosition, widgets);

        dashboard.setAvailableWidgetPosition(determineNextPosition(newAvailablePosition));

        update(dashboard);
        widgetRepository.update(widgets);
    }

    public Position recalculateWidgetPositions(Position position, List<Widget> widgets) {
        return widgets.stream()
                .filter(w -> applicableForPositionUpdate(position, w.getPosition()))
                .map(w -> determineNewPositionAfterRemoval(w.getPosition()))
                .reduce(this::determineLastPosition)
                .orElseThrow(); // TODO: throw dedicated exception
    }

    private boolean applicableForPositionUpdate(Position removedItemPosition, Position itemToUpdatePosition) {
        return false;
    }

    private Position determineNewPositionAfterRemoval(Position position) {
        return null;
    }

    private Position determineLastPosition(Position p1, Position p2) {
        return null;
    }
}
