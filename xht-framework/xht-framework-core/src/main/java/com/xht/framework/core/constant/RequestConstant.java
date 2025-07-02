package com.xht.framework.core.constant;

/**
 * 请求常量
 *
 * @author xht
 **/
public interface RequestConstant {

    // 请求头常量
    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_AUTH_KEY = "X-Auth-Key";
    String HEADER_USER_ID = "X-User-Id";
    String HEADER_USER_ACCOUNT = "X-User-Account";
    String HEADER_TRACE_ID = "X-Trace-Id";
    String HEADER_TENANT_ID = "X-Tenant-Id";
    String HEADER_USER_AGENT = "User-Agent";
    String HEADER_CONTENT_TYPE = "Content-Type";
    String HEADER_ACCEPT = "Accept";


    // 内容类型常量
    String CONTENT_TYPE_JSON = "application/json";
    String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";
    String CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";
    String CONTENT_TYPE_XML = "application/xml";
    String CONTENT_TYPE_PLAIN_TEXT = "text/plain";

    // 请求方法常量
    String METHOD_GET = "GET";
    String METHOD_POST = "POST";
    String METHOD_PUT = "PUT";
    String METHOD_DELETE = "DELETE";
    String METHOD_PATCH = "PATCH";
    String METHOD_HEAD = "HEAD";
    String METHOD_OPTIONS = "OPTIONS";

    // 常用参数名
    String PARAM_PAGE = "page";
    String PARAM_SIZE = "size";
    String PARAM_SORT = "sort";
    String PARAM_KEYWORD = "keyword";
    String PARAM_START_TIME = "startTime";
    String PARAM_END_TIME = "endTime";
    /**
     * 当前时间戳参数名
     */
    String PARAM_NOW_TIMESTAMP = "_t";

    // 编码格式
    String CHARSET_UTF_8 = "UTF-8";
    String CHARSET_GBK = "GBK";

    // 缓存控制
    String CACHE_CONTROL_NO_CACHE = "no-cache";
    String CACHE_CONTROL_PRIVATE = "private";

    // Session相关
    String SESSION_USER_KEY = "currentUser";
    String SESSION_LANGUAGE = "language";

}
