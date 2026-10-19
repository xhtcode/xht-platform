package com.xht.platform.client.blog;

import com.xht.framework.log.annotations.ConditionalOnBLog;
import com.xht.framework.log.configurers.BLogProperties;
import com.xht.framework.log.repository.BLogRepository;
import com.xht.platform.audit.service.IBLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 本地数据库日志自动配置
 * <p>与 JsonBLogRepository（default）、BLogRepositoryImpl（feign）三者条件互斥，
 * 同一时刻只会存在一个 BLogRepository。</p>
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
public class BLogJdbcAutoConfiguration {

    /**
     * jdbc 方式的 blog 日志仓库
     *
     * @param bLogService 系统日志服务
     * @return blog 日志仓库
     */
    @Bean
    @ConditionalOnBLog(value = BLogProperties.RepositoryType.JDBC)
    public BLogRepository jdbcBLogRepository(IBLogService bLogService) {
        log.info("创建 JDBC 日志 Repository Bean");
        return new BLogJdbcRepository(bLogService);
    }
}
