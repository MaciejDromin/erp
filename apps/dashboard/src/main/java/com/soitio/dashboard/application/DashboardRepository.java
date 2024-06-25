package com.soitio.dashboard.application;

import com.soitio.dashboard.domain.Dashboard;
import com.soitio.dashboard.domain.DashboardType;
import com.soitio.dashboard.domain.dto.DashboardCreationDto;
import com.soitio.dashboard.domain.dto.DashboardDto;
import com.soitio.dashboard.widget.application.WidgetRepository;
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
                .widgets(widgetRepository.createWidgets(dashboardCreation.getWidgets()))
                .name(dashboardCreation.getName())
                .type(dashboardCreation.getType())
                .defaultForType(defaultDashboard.isEmpty())
                .build());
    }

    public void setDashboardDefault(String dashboardId) {
        update("defaultForType = ?1 where _id = ?2", true, new ObjectId(dashboardId));
    }

    private DashboardDto to(Dashboard dashboard) {
        return DashboardDto.builder()
                .id(dashboard.getId().toString())
                .name(dashboard.getName())
                .type(dashboard.getType())
                .defaultForType(dashboard.isDefaultForType())
                // .widgets() TODO: implement this for widgets
                .build();
    }

    public void createWidgetInDashboard(WidgetCreationDto widgetCreation, String dashboardId) {
        Dashboard dashboard = findById(new ObjectId(dashboardId));
        dashboard.addWidget(widgetRepository.createWidget(widgetCreation));
        update(dashboard);
    }
}
