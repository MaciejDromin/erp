package com.soitio.reports.service.config;

import com.soitio.commons.ws.AbstractJobContent;
import com.soitio.commons.ws.ConcreteJobContent;
import com.soitio.commons.ws.WsMessage;
import com.soitio.reports.service.ws.domain.CompletedReportContent;
import com.soitio.reports.service.ws.domain.FailedReportContent;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {WsMessage.class,
        AbstractJobContent.class,
        ConcreteJobContent.class,
        CompletedReportContent.class,
        FailedReportContent.class
})
public class ReflectionConfig {
}
