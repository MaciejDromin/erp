package pl.mlisowski.finances.moneyoperation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMoneyOperation is a Querydsl query type for MoneyOperation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMoneyOperation extends EntityPathBase<MoneyOperation> {

    private static final long serialVersionUID = 521004895L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMoneyOperation moneyOperation = new QMoneyOperation("moneyOperation");

    public final pl.mlisowski.finances.common.persistence.QBaseEntity _super = new pl.mlisowski.finances.common.persistence.QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final StringPath currency = createString("currency");

    public final DateTimePath<java.time.ZonedDateTime> effectiveDate = createDateTime("effectiveDate", java.time.ZonedDateTime.class);

    public final EnumPath<java.time.Month> effectiveMonth = createEnum("effectiveMonth", java.time.Month.class);

    public final NumberPath<Integer> effectiveYear = createNumber("effectiveYear", Integer.class);

    public final pl.mlisowski.finances.operationcategories.domain.QOperationCategory operationCategory;

    public final StringPath operationDescription = createString("operationDescription");

    public final EnumPath<MoneyOperationType> operationType = createEnum("operationType", MoneyOperationType.class);

    //inherited
    public final StringPath uuid = _super.uuid;

    public QMoneyOperation(String variable) {
        this(MoneyOperation.class, forVariable(variable), INITS);
    }

    public QMoneyOperation(Path<? extends MoneyOperation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMoneyOperation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMoneyOperation(PathMetadata metadata, PathInits inits) {
        this(MoneyOperation.class, metadata, inits);
    }

    public QMoneyOperation(Class<? extends MoneyOperation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.operationCategory = inits.isInitialized("operationCategory") ? new pl.mlisowski.finances.operationcategories.domain.QOperationCategory(forProperty("operationCategory")) : null;
    }

}

