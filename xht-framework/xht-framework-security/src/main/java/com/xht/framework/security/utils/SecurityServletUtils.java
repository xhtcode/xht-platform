package com.xht.framework.security.utils;

import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.enums.CharacterEnums;
import com.xht.framework.core.jackson.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

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
     * @param data       Object
     */
    public static void writeString(HttpServletResponse response, Object data) {
        response.setStatus(HttpStatus.OK.value());
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setCharacterEncoding(CharacterEnums.UTF_8.getValue());
        try {
            response.setContentType(HttpConstants.ContentType.APPLICATION_JSON_UTF8_VALUE.getValue());
            response.getWriter().write(JsonUtils.toJsonString(data));
            response.getWriter().flush();
        } catch (IOException ex) {
            log.error("写回错误信息失败", ex);
        }
    }

}
