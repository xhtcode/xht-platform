package com.xht.framework.log.autoconfigure;

import com.xht.framework.log.annotations.ConditionalOnBLog;
import com.xht.framework.log.configurers.BLogProperties;
import com.xht.framework.log.repository.BLogRepository;
import com.xht.framework.log.repository.impl.JsonBLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 日志Repository自动装配
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(BLogProperties.class)
public class BLogRepositoryAutoConfiguration {

    /**
     * 创建默认的日志 Repository Bean
     * <p>当容器中不存在 BLogRepository 类型的 Bean 时，创建此默认 Bean</p>
     *
     * @return BLogRepository 日志仓库实例，用于处理日志保存操作
     */
    @Bean
    @ConditionalOnBLog(value = BLogProperties.RepositoryType.DEFAULT)
    public BLogRepository jsonBLogRepository() {
        log.info("创建默认的日志 Repository Bean");
        return new JsonBLogRepository();
    }


}
