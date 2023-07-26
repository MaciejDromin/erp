package pl.mlisowski.finances.operationcategories.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.mlisowski.finances.operationcategories.domain.OperationCategory;

@Repository
public interface OperationCategoryRepository extends JpaRepository<OperationCategory, String>, QuerydslPredicateExecutor<OperationCategory> {
}
