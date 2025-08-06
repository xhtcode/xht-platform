package com.xht.boot.oauth2.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xht.boot.oauth2.entity.token.ClaimsHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

@ReadingConverter
@SuppressWarnings("all")
public class BytesToClaimsHolderConverter
        implements Converter<byte[], ClaimsHolder> {

    private final Jackson2JsonRedisSerializer<ClaimsHolder> serializer;

    public BytesToClaimsHolderConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper
                .registerModules(SecurityJackson2Modules.getModules(BytesToClaimsHolderConverter.class.getClassLoader()));
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        objectMapper.addMixIn(ClaimsHolder.class, ClaimsHolderMixin.class);
        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper,
                ClaimsHolder.class);
    }

    @Override
    public ClaimsHolder convert(byte[] value) {
        try {
            return this.serializer.deserialize(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
