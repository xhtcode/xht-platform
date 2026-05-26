package com.xht.framework.log.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 登录请求信息
 *
 * @author xht
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestInfo {

    /**
     * 请求参数信息
     */
    private Map<String, String> requestParams;

    /**
     * 请求头信息
     */
    private Map<String, String> headers;
}
