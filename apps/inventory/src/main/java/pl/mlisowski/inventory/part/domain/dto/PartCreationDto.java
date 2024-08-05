package pl.mlisowski.inventory.part.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class PartCreationDto {

    private String name;
    private String partNumber;
    private ObjectId manufacturerId;

}
