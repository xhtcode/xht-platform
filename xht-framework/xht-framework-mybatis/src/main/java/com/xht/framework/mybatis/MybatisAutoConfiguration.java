package com.xht.framework.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.xht.framework.mybatis.handlers.MybatisPlusMetaObjectHandler;
import com.xht.framework.mybatis.handlers.MybatisPlusSecurityMetaObjectHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 描述 ： mybatis 自动配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class MybatisAutoConfiguration {

    /**
     * mybatis-plus插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页拦截器，指定数据库类型为MySQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 添加乐观锁拦截器
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 禁全表更删插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> {
            GlobalConfig globalConfig = properties.getGlobalConfig();
            globalConfig.setBanner(false);
        };
    }

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
     */
    @Bean
    @ConditionalOnMissingClass(value = "org.springframework.security.core.context.SecurityContextHolder")
    public MetaObjectHandler metaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }

}

