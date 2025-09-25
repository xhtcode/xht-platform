package com.xht.auth.authorization.token;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * token 注销成功处理器
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
public class TokenRevocationAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ServletUtil.write(response, R.okMsg("token 注销成功"));
    }
}
