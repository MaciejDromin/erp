package com.soitio.dashboard.domain.dto;

import com.soitio.dashboard.domain.DashboardType;
import com.soitio.dashboard.widget.domain.dto.WidgetCreationDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DashboardCreationDto {

    private String name;

    @Builder.Default
    private List<WidgetCreationDto> widgets = List.of();

    private DashboardType type;

}
