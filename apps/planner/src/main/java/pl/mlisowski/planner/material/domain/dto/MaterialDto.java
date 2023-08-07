package pl.mlisowski.planner.material.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mlisowski.planner.common.dto.AmountDto;
import pl.mlisowski.planner.common.model.QuantityUnit;
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
