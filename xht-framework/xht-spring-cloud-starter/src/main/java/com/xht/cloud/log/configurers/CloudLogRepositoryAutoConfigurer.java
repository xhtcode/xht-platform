package com.xht.cloud.log.configurers;

import com.xht.cloud.log.feign.RemoteLogClientService;
import com.xht.cloud.log.feign.factory.RemoteLogClientFallbackFactory;
import com.xht.cloud.log.repository.FeignLogRepositoryImpl;
import com.xht.framework.log.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LogRepository 自动装配
 *
 * @author xht
 **/
@Slf4j
@Configuration
@ConditionalOnClass(FeignAutoConfiguration.class)
@EnableFeignClients(clients = RemoteLogClientService.class)
@ConditionalOnMissingBean(LogRepository.class)
public class CloudLogRepositoryAutoConfigurer {

    /**
     * FeignLogRepositoryImpl 实现类
     *
     * @return FeignLogRepositoryImpl 实现类
     */
    @Bean
    public LogRepository feignLogRepositoryImpl(RemoteLogClientService remoteLogClientService) {
        return new FeignLogRepositoryImpl(remoteLogClientService);
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
