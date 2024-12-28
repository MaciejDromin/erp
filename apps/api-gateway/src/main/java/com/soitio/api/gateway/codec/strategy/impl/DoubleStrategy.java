package com.soitio.api.gateway.codec.strategy.impl;

import com.soitio.api.gateway.codec.strategy.CodecStrategy;
import com.soitio.api.gateway.domain.ValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import java.util.List;

@ApplicationScoped
public class DoubleStrategy implements CodecStrategy<Double> {

    @Override
    public void write(BsonWriter bsonWriter, ValueWrapper valueWrapper) {
        bsonWriter.writeDouble((double) valueWrapper.value());
    }

    @Override
    public Double readBsonValue(BsonReader reader) {
        return reader.readDouble();
    }

    @Override
    public Class<Double> getSupportedClass() {
        return Double.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.DOUBLE;
    }
}
