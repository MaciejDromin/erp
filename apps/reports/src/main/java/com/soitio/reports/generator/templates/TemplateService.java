package com.soitio.reports.generator.templates;

import com.soitio.reports.Value;
import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

@ApplicationScoped
public class TemplateService {

    private static final Logger log = LoggerFactory.getLogger(TemplateService.class);

    private final Engine engine;

    public TemplateService(Engine engine) {
        this.engine = engine;
    }

    public String renderFromTemplate(String templateName, Map<String, Value> data) {
        Template template = engine.getTemplate(templateName);
        if (template == null) {
            // try to find custom template
        }
        try {
            return template.data(data).render();
        } catch (Exception e) {
            log.warn("Could not render from template {}", templateName);
            throw new IllegalStateException("Could not render from template " + templateName);
        }
    }

}
