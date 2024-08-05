package pl.mlisowski.inventory.maintenance.domain.dto;

import org.bson.types.ObjectId;

public record PartQuantity(ObjectId id, int quantity) {
}
