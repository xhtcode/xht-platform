package com.xht.framework.core.domain;

import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 请求信息封装类，用于存储HTTP请求的相关信息。
 *
 * @author xht
 * @param requestUrl 请求URL
 * @param paramMap 请求参数映射表
 * @param body 请求体内容
 * @param headerMap 请求头映射表
 * @param ip 请求IP
 * @param requestMethod 请求方法
 */
public record HttpServletRequestInfo(String requestUrl, Map<String, String> paramMap, String body,
                                     Map<String, String> headerMap, String ip, String requestMethod) {

    /**
     * 获取请求参数映射表。
     *
     * @return 返回请求参数映射表
     */
    public String getParamMap(String key) {
        return getParamMap(key, null);
    }

    /**
     * 获取请求参数映射表。
     *
     * @return 返回请求参数映射表
     */
    public String getParamMap(String key, String defaultValue) {
        if (CollectionUtils.isEmpty(this.paramMap)) {
            throw new UtilException("Please call the paramMap in the builder.");
        }
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return paramMap.getOrDefault(key, defaultValue);
    }


    /**
     * 获取请求头中的User-Agent信息。
     *
     * @return 返回User-Agent信息
     */
    public String getUserAgent() {
        return getHeader(HttpConstants.Header.USER_AGENT.getValue());
    }

    /**
     * 获取请求头中的指定名称的值。
     *
     * @param key 请求头名称
     * @return 返回请求头中的指定名称的值
     */
    public String getHeader(String key) {
        return getHeader(key, null);
    }

    /**
     * 获取请求头中的指定名称的值。
     *
     * @param key 请求头名称
     * @return 返回请求头中的指定名称的值
     */
    public String getHeader(String key, String defaultValue) {
        if (CollectionUtils.isEmpty(this.headerMap)) {
            throw new UtilException("Please call the headerMap in the builder.");
        }
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return headerMap.getOrDefault(key, defaultValue);
    }

    /**
     * 创建一个请求信息构建器实例。
     *
     * @param request HTTP请求对象
     * @return 返回一个新的RequestInfoBuilder实例
     */
    public static RequestInfoBuilder builder(HttpServletRequest request) {
        return new RequestInfoBuilder(request);
    }

    /**
     * 请求信息构建器类，用于逐步构建HttpServletRequestInfo对象。
     */
    public static class RequestInfoBuilder {

        /**
         * 原始HTTP请求对象
         */
        private final HttpServletRequest request;

        /**
         * 请求URL
         */
        private String requestUrl;

        /**
         * 请求参数映射表
         */
        private Map<String, String> paramMap;

        /**
         * 请求体内容
         */
        private String body;

        /**
         * 请求头映射表
         */
        private Map<String, String> headerMap;

        /**
         * 请求IP
         */
        private String ip;

        /**
         * 请求方法
         */
        private String requestMethod;

        /**
         * 构造函数，初始化构建器。
         *
         * @param request HTTP请求对象
         */
        private RequestInfoBuilder(HttpServletRequest request) {
            this.request = request;
        }

        /**
         * 设置请求URL。
         *
         * @return 返回当前构建器实例，支持链式调用
         */
        public RequestInfoBuilder requestUrl() {
            this.requestUrl = request.getRequestURI();
            return this;
        }

        /**
         * 设置请求参数映射表。
         *
         * @return 返回当前构建器实例，支持链式调用
         */
        public RequestInfoBuilder paramMap() {
            this.paramMap = ServletUtil.getParamMap(request);
            return this;
        }

        /**
         * 设置请求体内容。
         *
         * @return 返回当前构建器实例，支持链式调用
         */
        public RequestInfoBuilder body() {
            this.body = ServletUtil.getBody(request);
            return this;
        }

        /**
         * 设置请求头映射表。
         *
         * @return 返回当前构建器实例，支持链式调用
         */
        public RequestInfoBuilder headerMap() {
            this.headerMap = ServletUtil.getHeaderMap(request);
            return this;
        }

        /**
         * 获取请求IP。
         *
         * @return 返回请求IP
         */
        public RequestInfoBuilder ip() {
            this.ip = IpUtils.getIpAddr(request);
            return this;
        }

        /**
         * 获取请求IP。
         *
         * @return 返回请求IP
         */
        public RequestInfoBuilder requestMethod() {
            this.requestMethod = request.getMethod();
            return this;
        }

        /**
         * 构建并返回HttpServletRequestInfo对象。
         *
         * @return 返回构建完成的HttpServletRequestInfo实例
         */
        public HttpServletRequestInfo build() {
            return new HttpServletRequestInfo(this.requestUrl, this.paramMap, this.body, this.headerMap, this.ip, this.requestMethod);
        }

    }

}
