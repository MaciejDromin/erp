package com.soitio.dashboard.widget.web;

import com.soitio.dashboard.widget.application.WidgetRepository;
import com.soitio.dashboard.widget.definition.application.WidgetDefinitionRepository;
import com.soitio.dashboard.widget.definition.domain.dto.WidgetDefinitionDto;
import com.soitio.dashboard.widget.definition.domain.dto.WidgetDefinitionNameDto;
import com.soitio.dashboard.widget.domain.dto.WidgetPositionUpdateDto;
import com.soitio.dashboard.widget.domain.dto.WidgetUpdateDto;
import com.soitio.widgets.common.domain.WidgetDefinition;
import com.soitio.widgets.common.domain.WidgetDomain;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@Path("/widgets")
@RequiredArgsConstructor
public class WidgetController {

    private final WidgetRepository widgetRepository;
    private final WidgetDefinitionRepository widgetDefinitionRepository;

    @POST
    @Path("/definitions")
    public void registerWidgets(List<WidgetDefinition> widgetDefinitions) {
        widgetDefinitionRepository.createWidgets(widgetDefinitions);
    }

    @GET
    @Path("/definitions/domain/{widgetDomain}")
    public List<WidgetDefinitionNameDto> getAvailableWidgets(@PathParam("widgetDomain") WidgetDomain widgetDomain) {
        return widgetDefinitionRepository.getWidgetDefinitionNames(widgetDomain);
    }

    @GET
    @Path("/definitions/{widgetDefinitionId}")
    public WidgetDefinitionDto getWidgetDefinition(@PathParam("widgetDefinitionId") String widgetDefinitionId) {
        return widgetDefinitionRepository.getById(widgetDefinitionId);
    }

    @GET
    @Path("/{widgetId}/definition")
    public WidgetDefinitionDto getWidgetDefinitionByWidgetId(@PathParam("widgetId") String widgetId,
                                                             @HeaderParam("X-OrgId") String orgId) {
        return widgetDefinitionRepository.getById(widgetRepository
                .findByIdAndOrgId(widgetId, orgId).getWidgetDefinitionId().toString());
    }

    @POST
    @Path("/{widgetId}/filters")
    public void updateFilters(@PathParam("widgetId") String widgetId, Map<String, Object> filters,
                              @HeaderParam("X-OrgId") String orgId) {
        widgetRepository.updateWidgetFilters(widgetId, filters, orgId);
    }

    @POST
    @Path("/update-positions")
    public void updatePositions(List<WidgetPositionUpdateDto> toUpdate,
                                @HeaderParam("X-OrgId") String orgId) {
        widgetRepository.updatePositions(toUpdate, orgId);
    }

    @POST
    @Path("/{widgetId}")
    public void updateWidget(@PathParam("widgetId") String widgetId, WidgetUpdateDto update,
                             @HeaderParam("X-OrgId") String orgId) {
        widgetRepository.updateWidget(widgetId, update, orgId);
    }

}
