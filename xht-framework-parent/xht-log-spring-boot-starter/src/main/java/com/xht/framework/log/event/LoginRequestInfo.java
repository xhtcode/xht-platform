package com.xht.framework.log.event;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录请求信息
 *
 * @author xht
 **/
@Data
public class LoginRequestInfo {

    /**
     * 请求参数信息
     */
    private Map<String, String> requestParams;

    /**
     * 请求头信息
     */
    private Map<String, String> headers;

    /**
     * 扩展信息
     */
    @JsonAnyGetter
    private Map<String, Object> extend;

    public LoginRequestInfo() {
    }

    public LoginRequestInfo(Map<String, String> requestParams, Map<String, String> headers) {
        this.requestParams = requestParams;
        this.headers = headers;
        this.extend = new HashMap<>();
    }

    public void addExtend(String key, Object value) {
        this.extend.put(key, value);
    }

}
