package com.xht.framework.core.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 描述 ：json工具类
 *
 * @author xht
 **/
@Slf4j
public final class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
        // 禁止实例化工具类
        throw new UnsupportedOperationException("Utility classes cannot be instantiated.");
    }

    static {
        // 对象的所有字段全部列入，还是其他的选项，可以忽略null等
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知属性，防止json字符串中存在，java对象中不存在对应属性的情况出现错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知属性，防止json字符串中存在，java对象中不存在对应属性的情况出现错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModules(new CustomJacksonModule());
    }

    /**
     * Object 转化成 json字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new UtilException("json 序列化错误了", e);
        }
    }

    /**
     * 将json形式的字符串数据转换成单个对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(String json, Class<?> clazz) {
        if (StringUtils.hasText(json) && Objects.nonNull(clazz)) {
            try {
                if (Objects.equals(String.class, clazz)) {
                    return (T) json;
                }
                return (T) objectMapper.readValue(json, clazz);
            } catch (Exception e) {
                throw new UtilException("json 序列化有问题!", e);
            }
        }
        return null;
    }

    /**
     * 将json形式的字符串数据转换成多个对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.hasText(json) && Objects.nonNull(typeReference)) {
            try {
                if (typeReference.getType().equals(String.class)) {
                    return (T) json;
                }
                return objectMapper.readValue(json, typeReference);
            } catch (Exception e) {
                throw new UtilException("json 序列化有问题!", e);
            }
        }

        return null;
    }
}
