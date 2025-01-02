package com.soitio.api.gateway.codec.strategy.impl;

import com.soitio.api.gateway.codec.strategy.CodecStrategy;
import com.soitio.api.gateway.domain.ValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;

@ApplicationScoped
public class NullStrategy implements CodecStrategy<Void> {
    @Override
    public void write(BsonWriter bsonWriter, ValueWrapper valueWrapper) {
        bsonWriter.writeNull();
    }

    @Override
    public Void readBsonValue(BsonReader reader) {
        reader.readNull();
        return null;
    }

    @Override
    public Class<Void> getSupportedClass() {
        return Void.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.NULL;
    }
}
