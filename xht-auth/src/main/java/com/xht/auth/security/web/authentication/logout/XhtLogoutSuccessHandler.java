package com.xht.auth.security.web.authentication.logout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.BasicResponse;
import com.xht.framework.utils.ServletUtil;
import com.xht.framework.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.Serial;

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
        if (response == null) {
            log.warn("退出成功 response is null");
            return;
        }
        LogoutResponse logoutResponse = null;
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.debug("删除 session:{} ", session.getId());
        }
        // 获取请求参数中是否包含 回调地址
        String redirectUrl = ServletUtil.getParams(request, REDIRECT_URL);
        if (StringUtils.isEmpty(redirectUrl)) {
            redirectUrl = ServletUtil.getHeader(request, HttpHeaders.REFERER);
        }
        if (StringUtils.hasText(redirectUrl)) {
            logoutResponse = new LogoutResponse();
            logoutResponse.setRedirectUrl(redirectUrl);
            log.info("退出成功 redirectUrl={}", redirectUrl);

        }
        ServletUtil.writeJson(response, R.ok().msg("退出成功").build(logoutResponse));
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LogoutResponse extends BasicResponse {

        @Serial
        private static final long serialVersionUID = 1L;

        private String redirectUrl;
    }

}
