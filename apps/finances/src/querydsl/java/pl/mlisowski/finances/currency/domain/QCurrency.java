package pl.mlisowski.finances.currency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCurrency is a Querydsl query type for Currency
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCurrency extends EntityPathBase<Currency> {

    private static final long serialVersionUID = -505155565L;

    public static final QCurrency currency = new QCurrency("currency");

    public final pl.mlisowski.finances.common.persistence.QBaseEntity _super = new pl.mlisowski.finances.common.persistence.QBaseEntity(this);

    public final StringPath code = createString("code");

    public final StringPath currencyName = createString("currencyName");

    public final DatePath<java.time.LocalDate> effectiveDate = createDate("effectiveDate", java.time.LocalDate.class);

    public final NumberPath<java.math.BigDecimal> rate = createNumber("rate", java.math.BigDecimal.class);

    //inherited
    public final StringPath uuid = _super.uuid;

    public QCurrency(String variable) {
        super(Currency.class, forVariable(variable));
    }

    public QCurrency(Path<? extends Currency> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCurrency(PathMetadata metadata) {
        super(Currency.class, metadata);
    }

}

