package com.xht.auth.session;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.xht.auth.security.jackson2.XhtSecurityJackson2Modules;
import com.xht.framework.jackson.CustomJacksonModule;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;

@Slf4j
@Configuration
public class SpringSessionConfiguration implements BeanClassLoaderAware {

    private ClassLoader loader;

    /**
     * 配置Redis序列化器
     *
     * @return RedisSerializer
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        JsonMapper objectMapper =
                JsonMapper.builder()
                        .addModules(SecurityJackson2Modules.getModules(this.loader))
                        .addModules(new XhtSecurityJackson2Modules())
                        .build();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.ALWAYS);
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
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    @Override
    public void setBeanClassLoader(@Nonnull ClassLoader classLoader) {
        this.loader = classLoader;
    }
}
