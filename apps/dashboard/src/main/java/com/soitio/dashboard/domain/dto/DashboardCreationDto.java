package com.soitio.dashboard.domain.dto;

import com.soitio.dashboard.domain.DashboardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DashboardCreationDto {

    private String name;

    private DashboardType type;

}
