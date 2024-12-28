package com.soitio.api.gateway.codec.strategy.impl;

import com.soitio.api.gateway.codec.strategy.CodecStrategy;
import com.soitio.api.gateway.domain.ValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import java.util.List;

@ApplicationScoped
public class Int64Strategy implements CodecStrategy<Long> {

    @Override
    public void write(BsonWriter bsonWriter, ValueWrapper valueWrapper) {
        bsonWriter.writeInt64((long) valueWrapper.value());
    }

    @Override
    public Long readBsonValue(BsonReader reader) {
        return reader.readInt64();
    }

    @Override
    public Class<Long> getSupportedClass() {
        return Long.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.INT64;
    }
}
