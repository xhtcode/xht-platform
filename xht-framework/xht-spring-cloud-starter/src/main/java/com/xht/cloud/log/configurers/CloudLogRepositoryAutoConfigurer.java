package com.xht.cloud.log.configurers;

import com.xht.boot.log.configurers.BootLogRepositoryAutoConfigurer;
import com.xht.cloud.log.feign.RemoteLogClientService;
import com.xht.cloud.log.feign.factory.RemoteLogClientFallbackFactory;
import com.xht.cloud.log.repository.FeignLogRepositoryImpl;
import com.xht.framework.log.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LogRepository 自动装配
 *
 * @author xht
 **/
@Slf4j
@Configuration
@AutoConfigureAfter(BootLogRepositoryAutoConfigurer.class)
@EnableFeignClients(clients = RemoteLogClientService.class)
@ConditionalOnMissingBean(LogRepository.class)
public class CloudLogRepositoryAutoConfigurer {

    /**
     * FeignLogRepositoryImpl 实现类
     *
     * @return FeignLogRepositoryImpl 实现类
     */
    @Bean
    public LogRepository feignLogRepositoryImpl() {
        return new FeignLogRepositoryImpl();
    }

    /**
     * RemoteLogClientFallbackFactory 实现类
     *
     * @return RemoteLogClientFallbackFactory 实现类
     */
    @Bean
    public RemoteLogClientFallbackFactory remoteLogClientFallbackFactory() {
        return new RemoteLogClientFallbackFactory();
    }
}
