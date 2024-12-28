package com.soitio.api.gateway.codec.strategy.impl;

import com.soitio.api.gateway.codec.strategy.CodecStrategy;
import com.soitio.api.gateway.domain.ValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import java.util.List;

@ApplicationScoped
public class BooleanStrategy implements CodecStrategy<Boolean> {

    @Override
    public void write(BsonWriter bsonWriter, ValueWrapper valueWrapper) {
        bsonWriter.writeBoolean((boolean) valueWrapper.value());
    }

    @Override
    public Boolean readBsonValue(BsonReader reader) {
        return reader.readBoolean();
    }

    @Override
    public Class<Boolean> getSupportedClass() {
        return Boolean.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.BOOLEAN;
    }
}
