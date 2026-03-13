package com.xht.auth.security.web.authentication.logout;

import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * 自定义退出成功处理器
 *
 * @author xht
 **/
@Slf4j
public class XhtLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final String REDIRECT_URL = "redirect_url";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (response == null) {
            log.warn("退出成功 response is null");
            return;
        }
        // 获取请求参数中是否包含 回调地址
        String redirectUrl = ServletUtil.getParams(request, REDIRECT_URL);
        if (StringUtils.isEmpty(redirectUrl)) {
            redirectUrl = ServletUtil.getHeader(request, HttpHeaders.REFERER);
        }
        if (StringUtils.hasText(redirectUrl)) {
            log.info("退出成功 redirectUrl={}", redirectUrl);
            response.sendRedirect(redirectUrl);
        }
    }

}
