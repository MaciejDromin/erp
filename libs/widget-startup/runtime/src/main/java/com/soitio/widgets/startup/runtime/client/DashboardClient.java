package com.soitio.widgets.startup.runtime.client;

import com.soitio.widgets.common.domain.WidgetDefinition;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@Path("/widgets/definitions")
@RegisterRestClient
public interface DashboardClient {

    @POST
    void uploadWidgetDefinitions(List<WidgetDefinition> widgetDefinitions);

}
