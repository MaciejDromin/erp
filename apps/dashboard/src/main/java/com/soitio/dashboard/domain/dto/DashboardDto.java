package com.soitio.dashboard.domain.dto;


import com.soitio.dashboard.domain.DashboardType;
import com.soitio.dashboard.widget.domain.dto.WidgetDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DashboardDto {

    private String id;
    private String name;

    @Builder.Default
    private List<WidgetDto> widgets = new ArrayList<>();

    private DashboardType type;

    private boolean defaultForType;

}
