package com.soitio.reports.service.ws.domain;

import com.soitio.commons.ws.AbstractJobContent;
import com.soitio.commons.ws.EventStatus;

public final class CompletedReportContent extends AbstractJobContent {

    private final String fileLocation;

    public CompletedReportContent(EventStatus status, String jobId, String fileLocation) {
        super(status, jobId);
        this.fileLocation = fileLocation;
    }

    public String getFileLocation() {
        return fileLocation;
    }
}
