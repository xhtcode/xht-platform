package com.xht.framework.log.event;

import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

/**
 * 登录日志 事件
 *
 * @author system
 */
@Setter
@Getter
public class LoginLogApplicationEvent extends ApplicationEvent {

    /**
     * 构造函数
     *
     * @param source 登录账号
     */
    public LoginLogApplicationEvent(String source, LogStatusEnums loginStatus) {
        super(source);
        this.userName = source;
        this.loginStatus = loginStatus;
    }

    /**
     * 链路追踪ID
     */
    private String traceId;

    /**
     * 应用名称（英文标识）
     */
    private String applicationName;

    /**
     * 应用名称（中文名称）
     */
    private String appName;

    /**
     * 登录账号
     */
    private final String userName;

    /**
     * 登录令牌Token
     */
    private String accessToken;

    /**
     * 登录类型：PASSWORD-密码登录 PHONE-手机号登录
     */
    private String loginType;

    /**
     * 登录IP地址
     */
    private String loginIp;

    /**
     * 浏览器/客户端标识
     */
    private String userAgent;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录状态：0-失败 1-成功
     */
    private final LogStatusEnums loginStatus;

    /**
     * 登录失败原因
     */
    private String loginFailReason;

    /**
     * 登录请求信息（参数+请求头+请求体）
     */
    private LoginRequestInfo loginRequestInfo;

    /**
     * 登录响应信息
     */
    private Object loginResponseInfo;
}