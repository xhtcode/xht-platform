package com.xht.boot.log.configurers;

import com.xht.boot.log.repository.JdbcLogRepositoryImpl;
import com.xht.framework.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * LogRepository 自动装配
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class BootLogRepositoryAutoConfigurer {

    /**
     * JdbcLogRepositoryImpl 实例化
     *
     * @return LogRepository
     */
    @Bean
    @ConditionalOnClass(JdbcTemplate.class)
    @ConditionalOnMissingBean(LogRepository.class)
    public LogRepository defaultLogRepository() {
        return new JdbcLogRepositoryImpl();
    }

}
