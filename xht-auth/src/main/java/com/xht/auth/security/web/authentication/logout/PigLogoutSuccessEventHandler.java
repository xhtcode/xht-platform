package com.xht.auth.security.web.authentication.logout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * 处理用户退出成功事件处理器
 *
 * @author lengleng
 * @date 2025/05/30
 */
@Slf4j
@Component
public class PigLogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

    /**
     * 处理登出成功事件
     * @param event 登出成功事件
     */
    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        log.info("用户退出成功{}",event.getSource().getClass());
        Authentication authentication = (Authentication) event.getSource();
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            handle(authentication);
        }
    }

    /**
     * 处理退出成功方法
     * <p>
     * 获取到登录的authentication 对象
     * @param authentication 登录对象
     */
    public void handle(Authentication authentication) {
        log.info("用户：{} 退出成功", authentication.getPrincipal());
    }

}
