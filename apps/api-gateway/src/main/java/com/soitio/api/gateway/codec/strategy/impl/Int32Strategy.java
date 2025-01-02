package com.soitio.api.gateway.codec.strategy.impl;

import com.soitio.api.gateway.codec.strategy.CodecStrategy;
import com.soitio.api.gateway.domain.ValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import java.util.List;

@ApplicationScoped
public class Int32Strategy implements CodecStrategy<Integer> {

    @Override
    public void write(BsonWriter bsonWriter, ValueWrapper valueWrapper) {
        bsonWriter.writeInt32((int) valueWrapper.value());
    }

    @Override
    public Integer readBsonValue(BsonReader reader) {
        return reader.readInt32();
    }

    @Override
    public Class<Integer> getSupportedClass() {
        return Integer.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.INT32;
    }
}