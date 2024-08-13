package com.soitio.planner.material.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.soitio.planner.common.dto.AmountDto;
import com.soitio.planner.common.model.QuantityUnit;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MaterialDto {

    private String id;
    private String name;
    private QuantityUnit unit;
    private AmountDto unitAmount;
    private String source;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedTime;

}
