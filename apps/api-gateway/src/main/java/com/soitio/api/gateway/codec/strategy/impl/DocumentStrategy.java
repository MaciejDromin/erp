package com.soitio.api.gateway.codec.strategy.impl;

import com.soitio.api.gateway.codec.strategy.CodecStrategy;
import com.soitio.api.gateway.codec.strategy.CodecStrategyProvider;
import com.soitio.api.gateway.domain.ValueWrapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DocumentStrategy implements CodecStrategy<Map<String, Object>> {

    private final CodecStrategyProvider strategyProvider;

    public DocumentStrategy(CodecStrategyProvider strategyProvider) {
        this.strategyProvider = strategyProvider;
    }

    @Override
    public void write(BsonWriter bsonWriter, ValueWrapper valueWrapper) {
        Map<String, Object> map = (Map<String, Object>) valueWrapper.value();
        bsonWriter.writeStartDocument();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            bsonWriter.writeName(entry.getKey());
            strategyProvider.getMapper(entry.getValue()).write(bsonWriter, new ValueWrapper(entry.getValue()));
        }
        bsonWriter.writeEndDocument();
    }

    @Override
    public Map<String, Object> readBsonValue(BsonReader reader) {
        Map<String, Object> ret = new HashMap<>();
        reader.readStartDocument();
        BsonType type = reader.readBsonType();
        while (type != BsonType.END_OF_DOCUMENT) {
            ret.put(reader.readName(), strategyProvider.getMapper(type).readBsonValue(reader));
            type = reader.readBsonType();
        }
        reader.readEndDocument();
        return ret;
    }

    @Override
    public Class<Map<String, Object>> getSupportedClass() {
        return (Class<Map<String, Object>>)(Class<?>) Map.class;
    }

    @Override
    public BsonType getBsonType() {
        return BsonType.DOCUMENT;
    }
}
