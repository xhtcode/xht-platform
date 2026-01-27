package com.xht.framework.core.utils;

import cn.hutool.core.collection.ArrayIter;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.CaseInsensitiveMap;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.enums.CharacterEnums;
import com.xht.framework.core.jackson.JsonUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * http servlet工具类
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
public final class ServletUtil {

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    /**
     * 获取HttpServletRequest对象
     *
     * @return HttpServletRequest对象
     */
    public static Optional<HttpServletRequest> getOptHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return Optional.empty();
        }
        return Optional.of(((ServletRequestAttributes) requestAttributes).getRequest());
    }

    /**
     * 获取HttpServletRequest对象
     *
     * @return HttpServletRequest对象
     */
    public static HttpServletRequest getHttpServletRequest() {
        return getOptHttpServletRequest().orElse(null);
    }

    // --------------------------------------------------------- getParam start

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    public static Map<String, String[]> getParams(ServletRequest request) {
        final Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    /**
     * 请求对象构建MultiValueMap.
     *
     * @param request 请求对象
     * @return MultiValueMap
     */
    public static Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
            params.put(entry.getKey(), ArrayUtil.join(entry.getValue(), StrUtil.COMMA));
        }
        return params;
    }

    /**
     * 获取请求体<br>
     * 调用该方法后，getParam方法将失效
     *
     * @param request {@link ServletRequest}
     * @return 获得请求体
     * @since 4.0.2
     */
    public static String getBody(ServletRequest request) {
        try (final BufferedReader reader = request.getReader()) {
            return IoUtil.read(reader);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获取请求体byte[]<br>
     * 调用该方法后，getParam方法将失效
     *
     * @param request {@link ServletRequest}
     * @return 获得请求体byte[]
     * @since 4.0.2
     */
    public static byte[] getBodyBytes(ServletRequest request) {
        try {
            return IoUtil.readBytes(request.getInputStream());
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
    // --------------------------------------------------------- getParam end
    // --------------------------------------------------------- Header start

    /**
     * 获取请求所有的头（header）信息
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return header值
     * @since 4.6.2
     */
    public static Map<String, String> getHeaderMap(HttpServletRequest request) {
        final Map<String, String> headerMap = new HashMap<>();

        final Enumeration<String> names = request.getHeaderNames();
        String name;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            headerMap.put(name, request.getHeader(name));
        }

        return headerMap;
    }

    /**
     * 获得请求header中的信息
     *
     * @param request     请求对象{@link HttpServletRequest}
     * @param name        头信息的KEY
     * @param charsetName 字符集
     * @return header值
     */
    public static String getHeader(HttpServletRequest request, String name, String charsetName) {
        return request.getHeader(name);
    }

    // --------------------------------------------------------- Header end

    // --------------------------------------------------------- Cookie start

    /**
     * 获得指定的Cookie
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               cookie名
     * @return Cookie对象
     */
    public static Cookie getCookie(HttpServletRequest httpServletRequest, String name) {
        return readCookieMap(httpServletRequest).get(name);
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return Cookie map
     */
    public static Map<String, Cookie> readCookieMap(HttpServletRequest httpServletRequest) {
        final Cookie[] cookies = httpServletRequest.getCookies();
        if (ArrayUtil.isEmpty(cookies)) {
            return MapUtil.empty();
        }

        return IterUtil.toMap(
                new ArrayIter<>(httpServletRequest.getCookies()),
                new CaseInsensitiveMap<>(),
                Cookie::getName);
    }

    /**
     * 设定返回给客户端的Cookie
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param cookie   Servlet Cookie对象
     */
    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    /**
     * 设定返回给客户端的Cookie
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param name     Cookie名
     * @param value    Cookie值
     */
    public static void addCookie(HttpServletResponse response, String name, String value) {
        response.addCookie(new Cookie(name, value));
    }

    /**
     * 设定返回给客户端的Cookie
     *
     * @param response        响应对象{@link HttpServletResponse}
     * @param name            cookie名
     * @param value           cookie值
     * @param maxAgeInSeconds -1: 关闭浏览器清除Cookie. 0: 立即清除Cookie. 0 : Cookie存在的秒数.
     * @param path            Cookie的有效路径
     * @param domain          the domain name within which this cookie is visible; form is according to RFC 2109
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds, String path, String domain) {
        Cookie cookie = new Cookie(name, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setPath(path);
        addCookie(response, cookie);
    }

    /**
     * 设定返回给客户端的Cookie<br>
     * Path: "/"<br>
     * No Domain
     *
     * @param response        响应对象{@link HttpServletResponse}
     * @param name            cookie名
     * @param value           cookie值
     * @param maxAgeInSeconds -1: 关闭浏览器清除Cookie. 0: 立即清除Cookie. 0 : Cookie存在的秒数.
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        addCookie(response, name, value, maxAgeInSeconds, "/", null);
    }

    // --------------------------------------------------------- Cookie end

    /**
     * @param request HttpServletRequest
     * @param name    请求参数key
     * @return 请求参数value
     */
    public static String getParams(HttpServletRequest request, String name) {
        if (Objects.isNull(request)) return null;
        return request.getParameter(name);
    }

    // --------------------------------------------------------- Response start


    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param obj      待渲染对象
     */
    public static void writeJson(HttpServletResponse response, Object obj) {
        PrintWriter writer = null;
        try {
            response.setStatus(HttpStatus.OK.value());
            // 允许跨域
            response.setHeader("Access-Control-Allow-Origin", "*");
            // 允许自定义请求头token(允许head跨域)
            response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding(CharacterEnums.UTF_8.getValue());
            writer = response.getWriter();
            writer.write(JsonUtils.toJsonString(obj));
            writer.flush();
        } catch (IOException e) {
            log.error("响应错误 {}", e.getMessage(), e);
        } finally {
            if (Objects.nonNull(writer)) {
                try {
                    writer.close();
                } catch (Exception ignored) {
                    log.debug("流关闭失败 {}", ignored.getMessage(), ignored);
                }
            }
        }
    }


    // --------------------------------------------------------- Response end
}
