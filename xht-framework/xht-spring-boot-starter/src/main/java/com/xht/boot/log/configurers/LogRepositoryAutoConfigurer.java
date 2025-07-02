package com.xht.boot.log.configurers;

import com.xht.boot.log.feign.RemoteLogClientService;
import com.xht.boot.log.repository.DefaultLogRepositoryImpl;
import com.xht.boot.log.repository.FeignLogRepositoryImpl;
import com.xht.framework.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * LogRepository 自动装配
 *
 * @author xht
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LogRepositoryAutoConfigurer {

    @Bean
    @Order(2)
    @ConditionalOnBean(RemoteLogClientService.class)
    @ConditionalOnMissingBean(LogRepository.class)
    public LogRepository feignLogRepositoryImpl() {
        return new FeignLogRepositoryImpl();
    }

    @Bean
    @Order(3)
    @ConditionalOnMissingBean(LogRepository.class)
    public LogRepository defaultLogRepository() {
        return new DefaultLogRepositoryImpl();
    }


}
