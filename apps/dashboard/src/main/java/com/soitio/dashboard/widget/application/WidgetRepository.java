package com.soitio.dashboard.widget.application;

import com.soitio.dashboard.widget.domain.Widget;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WidgetRepository implements PanacheMongoRepository<Widget> {
}
