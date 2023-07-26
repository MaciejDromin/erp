package pl.mlisowski.inventory.item.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.mlisowski.inventory.category.domain.dto.CategoryDto;
import pl.mlisowski.inventory.item.domain.ItemUnit;

import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
public class InventoryItemDto {

    private String id;
    private String name;
    private Integer quantity;
    private ItemUnit unit;
    private Set<CategoryDto> categories;

}
