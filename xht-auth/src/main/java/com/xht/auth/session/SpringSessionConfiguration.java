package com.xht.auth.session;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.xht.framework.core.jackson.mixin.HashSetMixin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;

import java.util.HashSet;

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
        JsonMapper objectMapper = JsonMapper.builder().addModules(SecurityJackson2Modules.getModules(this.loader)).build();
        // 注册HashSet的Mixin，允许反序列化
        objectMapper.addMixIn(HashSet.class, HashSetMixin.class);
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }
}
