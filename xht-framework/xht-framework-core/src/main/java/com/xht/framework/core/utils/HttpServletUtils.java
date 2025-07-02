package com.xht.framework.core.utils;

import com.xht.framework.core.jackson.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * http servlet工具类
 *
 * @author xht
 **/
@Slf4j
public final class HttpServletUtils {


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

    /**
     * 请求对象构建MultiValueMap.
     *
     * @param request 请求对象
     * @return MultiValueMap
     */
    public static Map<String, Object> getMapParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> parameters = new HashMap<>();
        parameterMap.forEach((k, v) -> parameters.put(k, v.length >= 1 ? v[0] : v));
        return parameters;
    }

    /**
     * @param request
     * @param name
     * @return
     */
    public static String getParams(HttpServletRequest request, String name) {
        if (Objects.isNull(request)) return null;
        return request.getParameter(name);
    }


    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param obj      待渲染对象
     */
    public static void writeString(HttpServletResponse response, Object obj) {
        writeString(response, HttpStatus.OK, obj);
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param obj      待渲染对象
     */
    public static void writeString(HttpServletResponse response, HttpStatus httpStatus, Object obj) {
        PrintWriter writer = null;
        try {
            response.setStatus(httpStatus.value());
            // 允许跨域
            response.setHeader("Access-Control-Allow-Origin", "*");
            // 允许自定义请求头token(允许head跨域)
            response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            writer = response.getWriter();
            writer.print(JsonUtils.toJsonString(obj));
        } catch (IOException e) {
            log.error("响应错误 {}", e.getMessage(), e);
        } finally {
            if (Objects.nonNull(writer)) {
                try {
                    writer.flush();
                    writer.close();
                } catch (Exception ignored) {

                }
            }
        }
    }

}
