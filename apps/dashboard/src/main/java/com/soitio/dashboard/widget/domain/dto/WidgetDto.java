package com.soitio.dashboard.widget.domain.dto;

import com.soitio.dashboard.common.Position;
import com.soitio.dashboard.common.WidgetDomain;
import com.soitio.dashboard.widget.domain.WidgetType;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class WidgetDto {

    private String id;
    private String name;

    @Builder.Default
    private Map<String, Object> filters = new HashMap<>();

    private WidgetType widgetType;
    private WidgetDomain widgetDomain;
    private String datasource;
    private Position position;

}
