package com.xht.auth.security.web.authentication.phone;

import com.xht.auth.security.web.authentication.AbstractXhtLoginConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 登录配置
 *
 * @author xht
 **/
@Slf4j
public class XhtPhoneLoginConfigurer extends AbstractXhtLoginConfigurer<XhtPhoneLoginConfigurer> {

    @Override
    public void init(HttpSecurity http) throws Exception {
        log.debug("XhtPhoneLoginConfigurer init");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.debug("XhtPhoneLoginConfigurer configure");
    }

    public static XhtPhoneLoginConfigurer phoneLogin() {
        return new XhtPhoneLoginConfigurer();
    }

}
