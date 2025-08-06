package com.xht.boot.oauth2.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xht.boot.oauth2.entity.token.ClaimsHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

@WritingConverter
@SuppressWarnings("all")
public class ClaimsHolderToBytesConverter implements Converter<ClaimsHolder, byte[]> {

    private final Jackson2JsonRedisSerializer<ClaimsHolder> serializer;

    public ClaimsHolderToBytesConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper
                .registerModules(SecurityJackson2Modules.getModules(ClaimsHolderToBytesConverter.class.getClassLoader()));
        objectMapper.registerModules(new OAuth2AuthorizationServerJackson2Module());
        objectMapper.addMixIn(ClaimsHolder.class, ClaimsHolderMixin.class);
        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, ClaimsHolder.class);
    }

    @Override
    public byte[] convert(ClaimsHolder value) {
        return this.serializer.serialize(value);
    }

}
