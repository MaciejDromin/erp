package pl.mlisowski.finances.objectvalues.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObjectValue is a Querydsl query type for ObjectValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObjectValue extends EntityPathBase<ObjectValue> {

    private static final long serialVersionUID = -1056327264L;

    public static final QObjectValue objectValue = new QObjectValue("objectValue");

    public final pl.mlisowski.finances.common.persistence.QBaseEntity _super = new pl.mlisowski.finances.common.persistence.QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final StringPath currency = createString("currency");

    public final StringPath objectId = createString("objectId");

    //inherited
    public final StringPath uuid = _super.uuid;

    public QObjectValue(String variable) {
        super(ObjectValue.class, forVariable(variable));
    }

    public QObjectValue(Path<? extends ObjectValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObjectValue(PathMetadata metadata) {
        super(ObjectValue.class, metadata);
    }

}

