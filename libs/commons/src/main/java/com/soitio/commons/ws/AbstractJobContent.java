package com.soitio.commons.ws;

public abstract class AbstractJobContent {

    private final EventStatus status;
    private final String jobId;

    protected AbstractJobContent(EventStatus status, String jobId) {
        this.status = status;
        this.jobId = jobId;
    }

    public EventStatus getStatus() {
        return status;
    }

    public String getJobId() {
        return jobId;
    }
}
