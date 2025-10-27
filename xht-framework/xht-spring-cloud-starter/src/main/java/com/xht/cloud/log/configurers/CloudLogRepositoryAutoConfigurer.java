package com.xht.cloud.log.configurers;

import com.xht.api.system.log.feign.RemoteLogClientService;
import com.xht.cloud.log.repository.FeignLogRepositoryImpl;
import com.xht.framework.log.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * LogRepository 自动装配
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnBean(RemoteLogClientService.class)
public class CloudLogRepositoryAutoConfigurer {

    /**
     * FeignLogRepositoryImpl 实现类
     *
     * @return FeignLogRepositoryImpl 实现类
     */
    @Bean
    @ConditionalOnMissingBean(LogRepository.class)
    public LogRepository feignLogRepositoryImpl(RemoteLogClientService remoteLogClientService) {
        return new FeignLogRepositoryImpl(remoteLogClientService);
    }

}
