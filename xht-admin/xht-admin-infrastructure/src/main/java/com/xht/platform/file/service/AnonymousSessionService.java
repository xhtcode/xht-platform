package com.xht.platform.file.service;

import com.xht.framework.utils.IdUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 描述： 匿名访问会话
 * <p>公开文件匿名访问时下发 xht_pv_anon Cookie，用于绑定匿名预览会话</p>
 *
 * @author xht
 **/
@Service
public class AnonymousSessionService {

    /**
     * Cookie 名称
     */
    private static final String COOKIE_NAME = "xht_pv_anon";

    /**
     * Cookie 有效期（秒）
     */
    private static final int COOKIE_MAX_AGE = 1800;

    /**
     * 获取或创建匿名会话ID
     *
     * @param request  请求
     * @param response 响应
     * @return 匿名会话ID
     */
    public String getOrCreate(HttpServletRequest request, HttpServletResponse response) {
        String existing = getExisting(request);
        if (existing != null) {
            return existing;
        }
        String sessionId = "an_" + IdUtils.simpleUUID();
        Cookie cookie = new Cookie(COOKIE_NAME, sessionId);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);
        return sessionId;
    }

    /**
     * 读取已有匿名会话ID
     *
     * @param request 请求
     * @return 匿名会话ID，不存在返回 null
     */
    public String getExisting(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isBlank()) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
