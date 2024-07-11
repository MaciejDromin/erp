package com.soitio.dashboard.widget.web;

import com.soitio.dashboard.widget.application.WidgetRepository;
import com.soitio.dashboard.widget.definition.application.WidgetDefinitionRepository;
import com.soitio.widgets.common.domain.WidgetDefinition;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Path("/widgets")
@RequiredArgsConstructor
public class WidgetController {

    // TODO: Implement this class

    private final WidgetRepository widgetRepository;
    private final WidgetDefinitionRepository widgetDefinitionRepository;

    @POST
    @Path("/definitions")
    public void registerWidgets(List<WidgetDefinition> widgetDefinitions) {
        widgetDefinitionRepository.createWidgets(widgetDefinitions);
    }

    public void deleteWidget(String widgetId) {

    }

    public void updateWidget(Object toUpdate) {

    }

}
