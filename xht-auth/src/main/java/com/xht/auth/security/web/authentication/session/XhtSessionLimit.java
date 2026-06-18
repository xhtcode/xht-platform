package com.xht.auth.security.web.authentication.session;

import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionLimit;

/**
 * 自定义动态会话限制
 *
 * @author xht
 **/
@Slf4j
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
        Object principal = authentication.getPrincipal();
        if (principal instanceof BasicUserDetails userDetails) {
            log.info("当前登录用户信息:{}", userDetails.getUsername());
            return 1;
        }
        return 100;
    }

}
