package com.xht.auth.security.web.authentication;

import com.xht.auth.security.LoginLogUtils;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证成功处理
 *
 * @author xht
 **/
@Slf4j
@Setter
public class AuthorizationServerSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectUrl;
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest == null) {
            redirectUrl = determineTargetUrl(request, response, authentication);
        } else {
            String targetUrlParameter = getTargetUrlParameter();
            if (isAlwaysUseDefaultTargetUrl()
                    || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
                this.requestCache.removeRequest(request, response);
                redirectUrl = determineTargetUrl(request, response, authentication);
            } else {
                redirectUrl = savedRequest.getRedirectUrl();
            }
        }
        clearAuthenticationAttributes(request);
        Map<String, Object> targetUrlMap = new HashMap<>();
        targetUrlMap.put("targetUrl", getRedirectUrl(redirectUrl));
        LoginLogUtils.saveSuccessLog(request, authentication);
        ServletUtil.writeJson(response, R.ok().msg("登录成功").build(targetUrlMap));
    }

    /**
     * 获取重定向地址
     * @param redirectUrl 重定向地址
     * @return 重定向地址
     */
    private static String getRedirectUrl(String redirectUrl) throws IOException {
        boolean absoluteUrl = UrlUtils.isAbsoluteUrl(redirectUrl);
        System.out.println("absoluteUrl:"+absoluteUrl);
        if (absoluteUrl) {
            URL uri = new URL(redirectUrl);
            System.out.println("uri.getPath():"+uri.getPath());
            if (StringUtils.startsWithIgnoreCase(uri.getPath(), "/oauth/authorize")) {
                return redirectUrl;
            }
        }
        return null;
    }

}
