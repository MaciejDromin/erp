package pl.mlisowski.inventory.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CategoryDto {

    private String id;
    private String name;

}
