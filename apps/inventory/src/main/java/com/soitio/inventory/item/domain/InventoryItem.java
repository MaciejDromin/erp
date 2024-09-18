package com.soitio.inventory.item.domain;

import com.soitio.commons.models.inventory.ItemUnit;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "Item")
public class InventoryItem {

    private ObjectId id;
    private String name;
    private Integer quantity;
    private ItemUnit unit;

    @Builder.Default
    private Set<ObjectId> categories = new HashSet<>();

    public void addCategory(ObjectId categoryId) {
        this.categories.add(categoryId);
    }

}
