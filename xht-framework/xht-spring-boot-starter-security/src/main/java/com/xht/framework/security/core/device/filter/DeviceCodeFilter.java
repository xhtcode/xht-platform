package com.xht.framework.security.core.device.filter;

import com.xht.framework.security.core.device.provider.DeviceCodeProvider;
import com.xht.framework.common.domain.R;
import com.xht.framework.utils.ServletUtil;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.spring.SpringContextUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import java.util.Objects;

/**
 * 描述： 设备验证码过滤器
 *
 * @author xht
 **/
@Slf4j
@Setter
public class DeviceCodeFilter extends GenericFilterBean {

    private DeviceCodeProvider deviceCodeProvider;

    private String deviceCodeName;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            doFilter((HttpServletRequest) request, (HttpServletResponse) response);
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("设备验证码过滤器异常 {}", e.getMessage(), e);
            ServletUtil.writeJson((HttpServletResponse) response, HttpStatus.INTERNAL_SERVER_ERROR, R.error().msg("设备安全验证未通过，请更换设备重试").build());
        }
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Cookie[] cookies = request.getCookies();
        Cookie deviceCodeCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (StringUtils.equals(name, deviceCodeName)) {
                    deviceCodeCookie = cookie;
                    // 验证设备验证码
                    boolean verifyDeviceCode = deviceCodeProvider.verifyDeviceCode(request, cookie.getValue());
                    if (!verifyDeviceCode) {
                        throw new ServletException("设备验证码不合法");
                    }
                }
            }
        }
        if (Objects.isNull(deviceCodeCookie)) {
            // 设备验证码不存在 要创建一个新的设备码
            String deviceCode = deviceCodeProvider.generateDeviceCode(request);
            Cookie cookie = new Cookie(deviceCodeName, deviceCode);
            cookie.setMaxAge(-1);// 有效期
            cookie.setPath("/"); // 全站生效（必须加，否则只当前接口路径可见）
            cookie.setHttpOnly(true); // JS 无法读取，防 XSS（推荐）
            if (Objects.equals(SpringContextUtils.getActiveProfile(), "prod")) {
                cookie.setSecure(true); // 仅 HTTPS 下传输，生产环境开启
            }
            response.addCookie(cookie);// 写入响应头返回浏览器
        }
    }

}
