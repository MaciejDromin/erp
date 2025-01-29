package com.soitio.reports.service.domain;

import com.soitio.reports.JobStatus;

public record InitialReportStatusDto(String jobId, JobStatus status) {
}
