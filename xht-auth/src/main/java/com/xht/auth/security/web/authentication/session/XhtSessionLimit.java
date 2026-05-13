package com.xht.auth.security.web.authentication.session;

import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionLimit;
import org.springframework.stereotype.Component;

/**
 * 自定义动态会话限制
 *
 * @author xht
 **/
@Slf4j
@Component
public class XhtSessionLimit implements SessionLimit {


    /**
     * Applies this function to the given argument.
     *
     * @param authentication the function argument
     * @return the function result
     */
    @Override
    public Integer apply(Authentication authentication) {
        // 获取当前登录用户信息
        BasicUserDetails loginUser = (BasicUserDetails) authentication.getPrincipal();
        log.info("当前登录用户信息:{}", loginUser.getUsername());
        return 100;
    }
}
