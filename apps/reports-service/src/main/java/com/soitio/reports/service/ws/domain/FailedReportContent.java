package com.soitio.reports.service.ws.domain;

import com.soitio.commons.ws.AbstractJobContent;
import com.soitio.commons.ws.EventStatus;

public final class FailedReportContent extends AbstractJobContent {

    private final String reason;

    public FailedReportContent(EventStatus status, String jobId, String reason) {
        super(status, jobId);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
