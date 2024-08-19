package com.soitio.dashboard.widget.web;

import com.soitio.dashboard.widget.application.WidgetRepository;
import com.soitio.dashboard.widget.definition.application.WidgetDefinitionRepository;
import com.soitio.dashboard.widget.definition.domain.WidgetDefinitionEntity;
import com.soitio.dashboard.widget.definition.domain.dto.WidgetDefinitionDto;
import com.soitio.dashboard.widget.definition.domain.dto.WidgetDefinitionNameDto;
import com.soitio.widgets.common.domain.WidgetDefinition;
import com.soitio.widgets.common.domain.WidgetDomain;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Path("/widgets")
@RequiredArgsConstructor
public class WidgetController {

    // TODO: Implement this class

    private final WidgetRepository widgetRepository;
    private final WidgetDefinitionRepository widgetDefinitionRepository;

    @GET
    public List<WidgetDefinitionEntity> test() {
        return widgetDefinitionRepository.listAll();
    }

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

    public void updateWidget(Object toUpdate) {

    }

}
