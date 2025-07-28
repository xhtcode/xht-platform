package com.xht.framework.core.constant;

import com.xht.framework.core.exception.UtilException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HTTP协议相关常量工具类
 * 统一管理HTTP请求中常用的头部信息、内容类型和字符集等常量
 *
 * @author xht
 */
@SuppressWarnings("unused")
public final class HttpConstants {

    /**
     * 私有构造方法，防止工具类被实例化
     */
    private HttpConstants() {
        throw new UtilException("禁止实例化此类");
    }

    /**
     * HTTP请求头常量枚举
     * 定义系统中常用的HTTP请求头字段
     */
    @Getter
    @AllArgsConstructor
    public enum Header {
        /**
         * 认证信息头部，通常用于携带Bearer令牌等认证信息
         */
        AUTHORIZATION("Authorization"),

        /**
         * 自定义认证密钥头部，用于系统内部服务间认证
         */
        AUTH_KEY("X-Auth-Key"),

        /**
         * 用户ID头部，用于传递当前操作的用户ID
         */
        USER_ID("X-User-Id"),

        /**
         * 用户账号头部，用于传递当前操作的用户账号
         */
        USER_ACCOUNT("X-User-Account"),

        /**
         * 分布式追踪ID头部，用于全链路追踪
         */
        TRACE_ID("X-Trace-Id"),

        /**
         * 租户ID头部，用于多租户系统中标识当前租户
         */
        TENANT_ID("X-Tenant-Id"),

        /**
         * 用户代理头部，用于标识发起请求的客户端信息（浏览器/设备等）
         */
        USER_AGENT("User-Agent"),

        /**
         * 内容类型头部，用于指定请求体的媒体类型
         */
        CONTENT_TYPE("Content-Type"),

        /**
         * 接受类型头部，用于指定客户端可接受的响应内容类型
         */
        ACCEPT("Accept");

        /**
         * 请求头字段的实际值
         */
        private final String value;
    }

    /**
     * HTTP内容类型常量枚举
     * 定义系统支持的各种MIME类型
     */
    @Getter
    @AllArgsConstructor
    public enum ContentType {
        /**
         * Atom XML格式，用于RSS类聚合内容
         */
        APPLICATION_ATOM_XML_VALUE("application/atom+xml"),

        /**
         * CBOR格式，一种二进制数据交换格式
         */
        APPLICATION_CBOR_VALUE("application/cbor"),

        /**
         * 表单提交格式，用于键值对形式的表单数据
         */
        APPLICATION_FORM_URLENCODED_VALUE("application/x-www-form-urlencoded"),

        /**
         * GraphQL响应格式
         */
        APPLICATION_GRAPHQL_RESPONSE_VALUE("application/graphql-response+json"),

        /**
         * JSON格式，用于JSON数据交换
         */
        APPLICATION_JSON_VALUE("application/json"),

        /**
         * UTF-8编码的JSON格式
         */
        APPLICATION_JSON_UTF8_VALUE("application/json;charset=UTF-8"),

        /**
         * 二进制流格式，用于未知类型的二进制数据
         */
        APPLICATION_OCTET_STREAM_VALUE("application/octet-stream"),

        /**
         * PDF文档格式
         */
        APPLICATION_PDF_VALUE("application/pdf"),

        /**
         * 问题详情JSON格式，用于API错误信息返回
         */
        APPLICATION_PROBLEM_JSON_VALUE("application/problem+json"),

        /**
         * UTF-8编码的问题详情JSON格式
         */
        APPLICATION_PROBLEM_JSON_UTF8_VALUE("application/problem+json;charset=UTF-8"),

        /**
         * 问题详情XML格式
         */
        APPLICATION_PROBLEM_XML_VALUE("application/problem+xml"),

        /**
         * Protobuf格式，用于高效二进制数据交换
         */
        APPLICATION_PROTOBUF_VALUE("application/x-protobuf"),

        /**
         * RSS XML格式，用于简易信息聚合
         */
        APPLICATION_RSS_XML_VALUE("application/rss+xml"),

        /**
         * 换行分隔的JSON格式，用于流式数据传输
         */
        APPLICATION_NDJSON_VALUE("application/x-ndjson"),

        /**
         * XHTML格式，结合XML和HTML的标记语言
         */
        APPLICATION_XHTML_XML_VALUE("application/xhtml+xml"),

        /**
         * XML格式，用于XML数据交换
         */
        APPLICATION_XML_VALUE("application/xml"),

        /**
         * YAML格式，用于配置文件和数据交换
         */
        APPLICATION_YAML_VALUE("application/yaml"),

        /**
         * GIF图片格式
         */
        IMAGE_GIF_VALUE("image/gif"),

        /**
         * JPEG图片格式
         */
        IMAGE_JPEG_VALUE("image/jpeg"),

        /**
         * PNG图片格式
         */
        IMAGE_PNG_VALUE("image/png"),

        /**
         * 多部分表单数据格式，用于文件上传
         */
        MULTIPART_FORM_DATA_VALUE("multipart/form-data"),

        /**
         * 多部分混合格式，用于包含不同类型的内容
         */
        MULTIPART_MIXED_VALUE("multipart/mixed"),

        /**
         * 多部分相关格式，用于包含有相互关系的多个部分
         */
        MULTIPART_RELATED_VALUE("multipart/related"),

        /**
         * 文本事件流格式，用于Server-Sent Events
         */
        TEXT_EVENT_STREAM_VALUE("text/event-stream"),

        /**
         * HTML格式，用于网页内容
         */
        TEXT_HTML_VALUE("text/html"),

        /**
         * Markdown格式，用于轻量级标记语言文本
         */
        TEXT_MARKDOWN_VALUE("text/markdown"),

        /**
         * 纯文本格式，用于普通文本内容
         */
        TEXT_PLAIN_VALUE("text/plain"),

        /**
         * 文本XML格式
         */
        TEXT_XML_VALUE("text/xml"),

        /**
         * 文本YAML格式
         */
        TEXT_YAML_VALUE("text/yaml"),

        /**
         * 质量因子参数，用于Accept头部中指定优先级
         */
        PARAM_QUALITY_FACTOR("q");

        /**
         * 内容类型的MIME类型字符串
         */
        private final String value;
    }

    /**
     * 字符集常量枚举
     * 定义系统支持的字符编码集
     */
    @Getter
    @AllArgsConstructor
    public enum Character {
        /**
         * UTF-8字符集，Unicode的可变长度字符编码
         */
        UTF8("UTF-8", "Unicode字符编码"),

        /**
         * GBK字符集，汉字内码扩展规范
         */
        GBK("GBK", "汉字内码扩展规范");

        /**
         * 字符集编码名称
         */
        private final String value;

        /**
         * 字符集描述信息
         */
        private final String info;
    }

    /**
     * HTTP请求方法常量枚举
     * 定义HTTP协议规范中的常用请求方法
     */
    @Getter
    @AllArgsConstructor
    public enum Method {
        /**
         * GET方法：用于请求获取指定资源
         * 是幂等的、安全的方法，请求参数通常放在URL中
         */
        GET("GET"),

        /**
         * POST方法：用于向服务器提交数据，创建新资源
         * 非幂等方法，请求参数通常放在请求体中
         */
        POST("POST"),

        /**
         * PUT方法：用于向服务器提交数据，全量更新指定资源
         * 是幂等方法，通常用于替换资源的全部内容
         */
        PUT("PUT"),

        /**
         * DELETE方法：用于请求服务器删除指定资源
         * 是幂等方法，执行后会移除目标资源
         */
        DELETE("DELETE"),

        /**
         * PATCH方法：用于对资源进行部分更新
         * 非幂等方法，只更新资源的部分字段
         */
        PATCH("PATCH"),

        /**
         * HEAD方法：类似于GET方法，但只请求资源的头部信息，不返回实体内容
         * 用于获取资源的元数据，如内容长度、修改时间等
         */
        HEAD("HEAD"),

        /**
         * OPTIONS方法：用于请求服务器支持的HTTP方法
         * 常用于跨域资源共享(CORS)预检请求，获取服务器允许的请求方法
         */
        OPTIONS("OPTIONS");

        /**
         * 请求方法的字符串表示
         */
        private final String value;
    }

}
