package com.xht.auth.security.web.authentication.logout;

import com.xht.framework.core.utils.mdc.TraceIdUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Enumeration;
import java.util.Objects;

/**
 * 自定义退出处理器
 *
 * @author xht
 **/
@Slf4j
public class XhtLogoutHandler implements LogoutHandler {

    /**
     * Causes a logout to be completed. The method must complete successfully.
     *
     * @param request        the HTTP request
     * @param response       the HTTP response
     * @param authentication the current principal details
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        TraceIdUtils.putTraceId(TraceIdUtils.generateTraceId());
        if (Objects.isNull(authentication)) {
            return;
        }
        HttpSession session = request.getSession(false);
        if (Objects.nonNull(session)) {
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String element = attributeNames.nextElement();
                System.out.println(element + ":" + session.getAttribute(element));
                session.invalidate();
            }
        }
        log.info("退出成功 开始存储退出登录日志");
    }
}
