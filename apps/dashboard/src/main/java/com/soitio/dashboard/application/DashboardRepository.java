package com.soitio.dashboard.application;

import com.soitio.dashboard.domain.Dashboard;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DashboardRepository implements PanacheMongoRepository<Dashboard> {

    public Object getDashboard(String dashboardId) {
        return null;
    }

    public Object getDashboardForType(String type) {
        return null;
    }

}
