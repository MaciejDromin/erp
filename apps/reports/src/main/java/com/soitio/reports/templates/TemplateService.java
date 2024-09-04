package com.soitio.reports.templates;

import com.soitio.reports.Value;
import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class TemplateService {

    private final Engine engine;

    public TemplateService(Engine engine) {
        this.engine = engine;
    }

    public String renderFromTemplate(String templateName, Map<String, Value> data) {
        Template template = engine.getTemplate(templateName);
        if (template == null) {
            // try to find custom template
        }
        return template.data(data).render();
    }

}
