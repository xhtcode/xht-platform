package com.xht.auth.security.web.authentication;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * token 注销成功处理器
 *
 * @author xht
 **/
@Slf4j
public class TokenRevocationAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful getAuthenticatedPrincipal
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the getAuthenticatedPrincipal process.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ServletUtil.writeJson(response, R.ok().msg("token 注销成功").build());
    }
}
