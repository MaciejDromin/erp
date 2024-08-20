package com.soitio.dashboard.widget.definition.codec;

import com.soitio.dashboard.widget.definition.codec.strategy.MapperStrategyProvider;
import com.soitio.widgets.common.domain.data.AnyPossibleValueWrapper;
import lombok.RequiredArgsConstructor;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

@RequiredArgsConstructor
public class AnyPossibleValueCodecProvider implements CodecProvider {

    private final MapperStrategyProvider mapperStrategyProvider;

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry codecRegistry) {
        if (clazz.equals(AnyPossibleValueWrapper.class)) {
            return (Codec<T>) new AnyPossibleValueCodec(mapperStrategyProvider);
        }
        return null;
    }
}
