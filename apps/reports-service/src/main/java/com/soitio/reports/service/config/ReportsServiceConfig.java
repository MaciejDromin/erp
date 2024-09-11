package com.soitio.reports.service.config;

import com.soitio.reports.client.ReportsClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@ApplicationScoped
public class ReportsServiceConfig {

    @ConfigProperty(name = "reports-generator.host")
    String host;

    @ConfigProperty(name = "reports-generator.port")
    Integer port;

    @Produces
    @Named("reportStatusExecutor")
    @ApplicationScoped
    public ExecutorService managedExecutorService() {
        ThreadFactory threadFactory = new NamedThreadFactory("report-status-updater");
        return Executors.newFixedThreadPool(10, threadFactory);
    }

    @Produces
    @ApplicationScoped
    public ReportsClient reportsClient() {
        return new ReportsClient(this.host, this.port);
    }

}
