package com.soitio.reports.service.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReportGenerationRequestDto {

    String name;
    String template;

}
