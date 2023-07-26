package pl.mlisowski.finances.operationcategories.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOperationCategory is a Querydsl query type for OperationCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOperationCategory extends EntityPathBase<OperationCategory> {

    private static final long serialVersionUID = -1779570981L;

    public static final QOperationCategory operationCategory = new QOperationCategory("operationCategory");

    public final pl.mlisowski.finances.common.persistence.QBaseEntity _super = new pl.mlisowski.finances.common.persistence.QBaseEntity(this);

    public final StringPath operationName = createString("operationName");

    public final EnumPath<pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType> operationType = createEnum("operationType", pl.mlisowski.finances.moneyoperation.domain.MoneyOperationType.class);

    //inherited
    public final StringPath uuid = _super.uuid;

    public QOperationCategory(String variable) {
        super(OperationCategory.class, forVariable(variable));
    }

    public QOperationCategory(Path<? extends OperationCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOperationCategory(PathMetadata metadata) {
        super(OperationCategory.class, metadata);
    }

}

