package com.xht.boot.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xht.boot.mybatis.handler.MybatisPlusMetaObjectHandler;
import com.xht.boot.mybatis.handler.MybatisPlusSecurityMetaObjectHandler;
import com.xht.framework.core.jackson.CustomJacksonModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 描述 ： mybatis 自动配置
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(SqlSessionFactory.class)
public class MybatisAutoConfiguration implements CommandLineRunner {

    public MybatisAutoConfiguration() {
        log.debug("[xht] |- xht-boot-mybatis 启动成功！");
    }

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

    /**
     * mybatis-plus全局配置
     *
     * @return mybatis-plus全局配置
     */
    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> {
            GlobalConfig globalConfig = properties.getGlobalConfig();
            globalConfig.setBanner(false);
        };
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {
        ObjectMapper objectMapper = new ObjectMapper();
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
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModules(new CustomJacksonModule());
        JacksonTypeHandler.setObjectMapper(objectMapper);
    }

    /**
     * 通用Mapper扫描接口
     *
     * @return MapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer commonMapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("com.xht.framework.mybatis.mapper.common");
        return scannerConfigurer;
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
     * 当没有spring security依赖时，使用普通元对象字段填充
     */
    @Bean
    @ConditionalOnMissingClass(value = "org.springframework.security.core.context.SecurityContextHolder")
    public MetaObjectHandler metaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }

}

