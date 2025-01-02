package com.soitio.api.gateway.codec.strategy.impl;

import com.soitio.api.gateway.codec.strategy.CodecStrategy;
import com.soitio.api.gateway.domain.ValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import java.util.List;

@ApplicationScoped
public class StringStrategy implements CodecStrategy<String> {

    @Override
    public void write(BsonWriter bsonWriter, ValueWrapper valueWrapper) {
        bsonWriter.writeString((String) valueWrapper.value());
    }

    @Override
    public String readBsonValue(BsonReader reader) {
        return reader.readString();
    }

    @Override
    public Class<String> getSupportedClass() {
        return String.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.STRING;
    }
}
