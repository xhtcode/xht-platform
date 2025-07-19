package com.xht.boot.mybatisplus.configurers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xht.boot.mybatisplus.handler.MybatisPlusMetaObjectHandler;
import com.xht.boot.mybatisplus.handler.MybatisPlusSecurityMetaObjectHandler;
import com.xht.framework.mybatis.MybatisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * MybatisPlus自动配置元对象字段填充
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnClass(MybatisAutoConfiguration.class)
public class MybatisPlusAutoMetaObjectAutoConfig {

    /**
     * 元对象字段填充
     * 当有spring security依赖时，使用安全元对象字段填充，否则使用普通元对象字段填充
     */
    @Bean
    @ConditionalOnClass(SecurityContextHolder.class)
    public MetaObjectHandler securityMetaObjectHandler() {
        return new MybatisPlusSecurityMetaObjectHandler();
    }

    /**
     * 元对象字段填充
     * 当没有spring security依赖时，使用普通元对象字段填充
     */
    @Bean
    @ConditionalOnMissingClass(value = "org.springframework.security.core.context.SecurityContextHolder")
    public MetaObjectHandler metaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }
}
