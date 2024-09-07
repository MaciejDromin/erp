package com.soitio.reports.generator.config;

import com.soitio.reports.client.ReportStatusClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@ApplicationScoped
public class ReportsConfig {

    @ConfigProperty(name = "reports-service.host")
    String host;

    @ConfigProperty(name = "reports-service.port")
    Integer port;

    @Produces
    @Named("reportExecutor")
    @ApplicationScoped
    public ExecutorService managedExecutorService() {
        ThreadFactory threadFactory = new NamedThreadFactory("report-generator");
        return Executors.newFixedThreadPool(10, threadFactory);
    }

    @Produces
    @ApplicationScoped
    public ReportStatusClient reportStatusClient() {
        return new ReportStatusClient(this.host, this.port);
    }

}
