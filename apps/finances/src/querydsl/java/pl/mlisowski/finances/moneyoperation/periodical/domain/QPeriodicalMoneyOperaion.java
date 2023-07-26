package pl.mlisowski.finances.moneyoperation.periodical.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPeriodicalMoneyOperaion is a Querydsl query type for PeriodicalMoneyOperaion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPeriodicalMoneyOperaion extends EntityPathBase<PeriodicalMoneyOperaion> {

    private static final long serialVersionUID = 1000357563L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPeriodicalMoneyOperaion periodicalMoneyOperaion = new QPeriodicalMoneyOperaion("periodicalMoneyOperaion");

    public final pl.mlisowski.finances.common.persistence.QBaseEntity _super = new pl.mlisowski.finances.common.persistence.QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final StringPath currency = createString("currency");

    public final EnumPath<java.time.Month> nextApplicableMonth = createEnum("nextApplicableMonth", java.time.Month.class);

    public final pl.mlisowski.finances.operationcategories.domain.QOperationCategory operationCategory;

    public final StringPath operationDescription = createString("operationDescription");

    public final EnumPath<pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType> operationType = createEnum("operationType", pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType.class);

    public final NumberPath<Integer> repetitionPeriod = createNumber("repetitionPeriod", Integer.class);

    //inherited
    public final StringPath uuid = _super.uuid;

    public QPeriodicalMoneyOperaion(String variable) {
        this(PeriodicalMoneyOperaion.class, forVariable(variable), INITS);
    }

    public QPeriodicalMoneyOperaion(Path<? extends PeriodicalMoneyOperaion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPeriodicalMoneyOperaion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPeriodicalMoneyOperaion(PathMetadata metadata, PathInits inits) {
        this(PeriodicalMoneyOperaion.class, metadata, inits);
    }

    public QPeriodicalMoneyOperaion(Class<? extends PeriodicalMoneyOperaion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.operationCategory = inits.isInitialized("operationCategory") ? new pl.mlisowski.finances.operationcategories.domain.QOperationCategory(forProperty("operationCategory")) : null;
    }

}

