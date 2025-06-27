package com.xht.framework.security.utils;

import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.jackson.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * SecurityServletUtils
 *
 * @author xht
 **/
@Slf4j
public final class SecurityServletUtils {

    /**
     * 写回错误信息
     *
     * @param response   HttpServletResponse
     * @param httpStatus HttpStatus
     * @param data       Object
     */
    public static void writeString(HttpServletResponse response, HttpStatus httpStatus, Object data) {
        response.setStatus(httpStatus.value());
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setCharacterEncoding(HttpConstants.Character.UTF8.getCode());
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(JsonUtils.toJsonString(data));
            response.getWriter().flush();
        } catch (IOException ex) {
            log.error("写回错误信息失败", ex);
        }

    }

}
