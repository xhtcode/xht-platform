package com.xht.boot.security.configurers;

import com.xht.boot.security.dao.DefaultUserDetailsDao;
import com.xht.framework.security.core.userdetails.UserDetailsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

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
    @ConditionalOnMissingBean(UserDetailsDao.class)
    public UserDetailsDao passWordUserDetailsStrategy() {
        return new DefaultUserDetailsDao();
    }

}
