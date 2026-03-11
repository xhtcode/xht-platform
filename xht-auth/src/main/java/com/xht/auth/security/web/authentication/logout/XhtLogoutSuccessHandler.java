package com.xht.auth.security.web.authentication.logout;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        ServletUtil.writeJson(response, R.ok().msg("退出成功").build());
    }

}
