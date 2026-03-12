package com.xht.auth.security.web.authentication.logout;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 自定义退出成功处理器
 *
 * @author xht
 **/
@Slf4j
public class XhtLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final String REDIRECT_URL = "redirect_url";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 获取请求参数中是否包含 回调地址
        String redirectUrl = request.getParameter(REDIRECT_URL);
        if (StringUtils.hasText(redirectUrl)) {
            redirectUrl = request.getHeader(HttpHeaders.REFERER);
        }
        log.info("退出成功 redirectUrl={}", redirectUrl);
        ServletUtil.writeJson(response, R.ok().msg("退出成功").build(redirectUrl));
    }

}
