package com.soitio.finances.operationcategories.application.port;

import com.soitio.finances.operationcategories.domain.OperationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationCategoryRepository extends JpaRepository<OperationCategory, String>,
        QuerydslPredicateExecutor<OperationCategory> {
}
