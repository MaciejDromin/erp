package pl.mlisowski.finances.receipt.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.mlisowski.finances.common.persistence.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class PurchaseAnalytics extends BaseEntity {
}
