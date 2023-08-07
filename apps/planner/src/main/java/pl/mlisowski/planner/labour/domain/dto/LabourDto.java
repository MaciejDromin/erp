package pl.mlisowski.planner.labour.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.mlisowski.planner.common.dto.AmountDto;
import pl.mlisowski.planner.common.model.QuantityUnit;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class LabourDto {

    private String id;
    private String name;
    private AmountDto rateAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedTime;
    private String contractorName;
    private String contractorContact;
    private QuantityUnit unit;

}
