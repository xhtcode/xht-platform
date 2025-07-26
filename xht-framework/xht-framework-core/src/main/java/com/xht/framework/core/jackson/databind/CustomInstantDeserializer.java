package com.xht.framework.core.jackson.databind;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.xht.framework.core.utils.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SuppressWarnings("unused")
public class CustomInstantDeserializer extends JsonDeserializer<Instant> implements ContextualDeserializer {

    private final DateTimeFormatter fmt;

    public CustomInstantDeserializer() {
        fmt = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MS_PATTERN).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    }

    public CustomInstantDeserializer(String pattern) {
        fmt = DateTimeFormatter.ofPattern(StringUtils.emptyToDefault(DatePattern.NORM_DATETIME_MS_PATTERN, pattern)).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    }


    @Override
    public Instant deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        return Instant.from(fmt.parse(p.getText()));
    }


    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty property) {
        return null;
    }
}