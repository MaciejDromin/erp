package com.soitio.dashboard.widget.domain.dto;

import com.soitio.dashboard.widget.domain.WidgetType;
import com.soitio.widgets.common.domain.WidgetDomain;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class WidgetCreationDto {

    private String name;

    @Builder.Default
    private Map<String, Object> filters = new HashMap<>();

    private WidgetType widgetType;
    private WidgetDomain widgetDomain;
    private String datasource;

}
