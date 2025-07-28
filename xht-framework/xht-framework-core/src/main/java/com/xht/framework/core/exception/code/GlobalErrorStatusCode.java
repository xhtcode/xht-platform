package com.xht.framework.core.exception.code;

import com.xht.framework.core.constant.basic.RConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.xht.framework.core.constant.basic.RConstants.FAIL;
import static com.xht.framework.core.constant.basic.RConstants.SUCCESS_MSG;

/**
 * 全局状态码枚举类
 * <p>
 * 这里藏着系统里各种成功与失败的小秘密
 * 用数字和文字记录着每一次交互的喜怒哀乐
 *
 * @author xht
 */
@Getter
@AllArgsConstructor
public enum GlobalErrorStatusCode implements ErrorCode {

    /**
     * 成功状态码
     */
    SUCCESS(RConstants.SUCCESS, SUCCESS_MSG),

    /**
     * 客户端错误状态码：400
     */
    BAD_REQUEST(400, "您的请求好像有点小脾气，我们没太看懂~"),

    /**
     * 客户端错误状态码：401
     */
    UNAUTHORIZED(401, "系统需要确认您的身份，就像进小区要刷门禁卡一样~"),

    /**
     * 客户端错误状态码：403
     */
    FORBIDDEN(403, "这个区域暂时不对您开放，就像电影院的VIP厅需要特殊票券去~"),

    /**
     * 客户端错误状态码：404
     */
    NOT_FOUND(404, "您要找的资源可能去喝下午茶了..."),

    /**
     * 客户端错误状态码：405
     */
    METHOD_NOT_ALLOWED(405, "这个操作方式我们不支持，就像用微波炉煮火锅不太合适~"),

    /**
     * 客户端错误状态码：415
     */
    PARAM_INVALID(415, "您传递的数据格式有点特别，我们需要适应一下~"),

    /**
     * 客户端错误状态码：424
     */
    TOKEN_EXPIRED(424, "您的访问凭证已经过期，需要重新获取~"),

    /**
     * 客户端错误状态码：429
     */
    TOO_MANY_REQUESTS(429, "慢点呀，系统快跟不上您的节奏了~"),

    /**
     * 服务器端错误状态码
     */
    ERROR(FAIL, "服务器正在伸懒腰，请稍后再来~"),

    /**
     * 服务器端错误状态码：503
     */
    SERVICE_UNAVAILABLE(503, "服务正在小憩，稍后再来看看吧~"),

    /**
     * 服务器端错误状态码：504
     */
    GATEWAY_TIMEOUT(504, " 请求传递过程中超时了，就像寄快递路上堵车了~");


    /**
     * 状态码数字标识
     */
    private final int code;

    /**
     * 状态描述信息
     */
    private final String msg;

}