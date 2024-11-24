package com.soitio.dashboard.widget.domain.dto;

import com.soitio.dashboard.common.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class WidgetPositionUpdateDto {

    private String id;
    private Position position;

}
