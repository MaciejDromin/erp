package com.soitio.dashboard.widget.application;

import com.soitio.dashboard.widget.domain.Widget;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class WidgetRepository implements PanacheMongoRepository<Widget> {

    public List<Widget> createWidgets(List<WidgetCreationDto> widgets) {
        return List.of();
    }

}
