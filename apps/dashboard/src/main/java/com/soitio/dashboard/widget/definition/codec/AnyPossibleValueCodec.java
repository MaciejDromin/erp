package com.soitio.dashboard.widget.definition.codec;

import com.soitio.dashboard.widget.definition.codec.strategy.MapperStrategyProvider;
import com.soitio.widgets.common.domain.data.AnyPossibleValueWrapper;
import lombok.RequiredArgsConstructor;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

@RequiredArgsConstructor
public class AnyPossibleValueCodec implements Codec<AnyPossibleValueWrapper> {

    private final MapperStrategyProvider mapperStrategyProvider;

    @Override
    public AnyPossibleValueWrapper decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return mapperStrategyProvider.getMapper(bsonReader.getCurrentBsonType()).read(bsonReader);
    }

    @Override
    public void encode(BsonWriter bsonWriter,
                       AnyPossibleValueWrapper anyPossibleValueWrapper,
                       EncoderContext encoderContext) {
        mapperStrategyProvider.getMapper(anyPossibleValueWrapper.value())
                .write(bsonWriter, anyPossibleValueWrapper);
    }

    @Override
    public Class<AnyPossibleValueWrapper> getEncoderClass() {
        return AnyPossibleValueWrapper.class;
    }

}
