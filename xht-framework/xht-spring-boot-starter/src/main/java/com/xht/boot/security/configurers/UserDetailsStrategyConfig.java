package com.xht.boot.security.configurers;

import com.xht.boot.security.strategy.PassWordUserDetailsStrategy;
import com.xht.framework.security.core.strategy.UserDetailsStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户查询配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
public class UserDetailsStrategyConfig {

    /**
     * 用户查询
     *
     * @return 查询
     */
    @Bean
    public UserDetailsStrategy passWordUserDetailsStrategy() {
        return new PassWordUserDetailsStrategy();
    }

}
