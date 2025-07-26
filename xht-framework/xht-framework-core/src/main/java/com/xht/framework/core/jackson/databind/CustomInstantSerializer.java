package com.xht.framework.core.jackson.databind;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.xht.framework.core.utils.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SuppressWarnings("unused")
public class CustomInstantSerializer extends JsonSerializer<Instant> {

    private final DateTimeFormatter fmt;

    public CustomInstantSerializer() {
        fmt = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MS_PATTERN).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    }

    public CustomInstantSerializer(String pattern) {
        fmt = DateTimeFormatter.ofPattern(StringUtils.emptyToDefault(DatePattern.NORM_DATETIME_MS_PATTERN, pattern)).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
    }

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String str = fmt.format(value);
        gen.writeString(str);
    }
}