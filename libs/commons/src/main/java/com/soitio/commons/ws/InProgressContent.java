package com.soitio.commons.ws;

public final class InProgressContent extends AbstractJobContent {

    public InProgressContent(EventStatus status, String jobId) {
        super(status, jobId);
    }
}
