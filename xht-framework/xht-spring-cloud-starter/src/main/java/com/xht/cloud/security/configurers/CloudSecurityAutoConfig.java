package com.xht.cloud.security.configurers;

import com.xht.api.system.user.feign.RemoteUserService;
import com.xht.cloud.security.dao.RemoteUserDetailsDao;
import com.xht.framework.security.configurers.SecurityAutoConfigurer;
import com.xht.framework.security.core.userdetails.UserDetailsDao;
import com.xht.framework.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * spring security  cloud starter 自动配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnClass(SecurityAutoConfigurer.class)
@AutoConfigureBefore(SecurityAutoConfigurer.class)
@ConditionalOnBean(RemoteUserService.class)
public class CloudSecurityAutoConfig {

    public CloudSecurityAutoConfig() {
        log.info("初始化 spring security  cloud starter 自动配置...");
    }


    /**
     * 用户服务
     *
     * @param remoteUserService 远程用户服务
     * @return 用户服务
     */
    @Bean
    public UserDetailsDao remoteUserDetailsDao(RemoteUserService remoteUserService, SecurityProperties securityProperties) {
        return new RemoteUserDetailsDao(remoteUserService, securityProperties);
    }

}
