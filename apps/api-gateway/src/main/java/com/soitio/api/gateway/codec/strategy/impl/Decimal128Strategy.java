package com.soitio.api.gateway.codec.strategy.impl;

import com.soitio.api.gateway.codec.strategy.CodecStrategy;
import com.soitio.api.gateway.domain.ValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class Decimal128Strategy implements CodecStrategy<BigDecimal> {

    @Override
    public void write(BsonWriter bsonWriter, ValueWrapper valueWrapper) {
        bsonWriter.writeDecimal128(Decimal128.parse(valueWrapper.value().toString()));
    }

    @Override
    public BigDecimal readBsonValue(BsonReader reader) {
        return reader.readDecimal128().bigDecimalValue();
    }

    @Override
    public Class<BigDecimal> getSupportedClass() {
        return BigDecimal.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.DECIMAL128;
    }
}
