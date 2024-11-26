package com.soitio.dashboard.widget.domain.dto;

import com.soitio.dashboard.widget.domain.WidgetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class WidgetUpdateDto {

    private String name;
    private WidgetType type;

}
