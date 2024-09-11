package com.soitio.reports.service.domain;

import com.soitio.reports.JobStatus;
import com.soitio.reports.service.common.persistence.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "report_status", indexes = {
        @Index(name = "jobId", columnList = "jobId")
})
public class ReportStatus extends BaseEntity {

    private String jobId;

    @Enumerated(value = EnumType.ORDINAL)
    private JobStatus status;
    private String location;
    private String message;

}
