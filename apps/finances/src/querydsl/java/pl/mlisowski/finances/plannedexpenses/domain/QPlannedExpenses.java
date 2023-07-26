package pl.mlisowski.finances.plannedexpenses.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlannedExpenses is a Querydsl query type for PlannedExpenses
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlannedExpenses extends EntityPathBase<PlannedExpenses> {

    private static final long serialVersionUID = -649641287L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlannedExpenses plannedExpenses = new QPlannedExpenses("plannedExpenses");

    public final pl.mlisowski.finances.common.persistence.QBaseEntity _super = new pl.mlisowski.finances.common.persistence.QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> actualAmount = createNumber("actualAmount", java.math.BigDecimal.class);

    public final StringPath currency = createString("currency");

    public final DateTimePath<java.time.ZonedDateTime> finalizedDate = createDateTime("finalizedDate", java.time.ZonedDateTime.class);

    public final pl.mlisowski.finances.operationcategories.domain.QOperationCategory operationCategory;

    public final StringPath operationDescription = createString("operationDescription");

    public final EnumPath<pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType> operationType = createEnum("operationType", pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType.class);

    public final NumberPath<java.math.BigDecimal> plannedAmount = createNumber("plannedAmount", java.math.BigDecimal.class);

    public final EnumPath<PlannedExpensesStatus> plannedExpensesStatus = createEnum("plannedExpensesStatus", PlannedExpensesStatus.class);

    public final EnumPath<java.time.Month> plannedMonth = createEnum("plannedMonth", java.time.Month.class);

    public final NumberPath<Integer> plannedYear = createNumber("plannedYear", Integer.class);

    //inherited
    public final StringPath uuid = _super.uuid;

    public QPlannedExpenses(String variable) {
        this(PlannedExpenses.class, forVariable(variable), INITS);
    }

    public QPlannedExpenses(Path<? extends PlannedExpenses> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlannedExpenses(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlannedExpenses(PathMetadata metadata, PathInits inits) {
        this(PlannedExpenses.class, metadata, inits);
    }

    public QPlannedExpenses(Class<? extends PlannedExpenses> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.operationCategory = inits.isInitialized("operationCategory") ? new pl.mlisowski.finances.operationcategories.domain.QOperationCategory(forProperty("operationCategory")) : null;
    }

}

