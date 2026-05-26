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
     * 链路跟踪ID
     */
    private String traceId;

    /**
     * 登录账号
     */
    private final String userName;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录IP地址
     */
    private String loginIp;

    /**
     * 登录状态：1-成功，0-失败
     */
    private final LogStatusEnums loginStatus;

    /**
     * 登录信息
     */
    private LoginRequestInfo loginRequestInfo;

    /**
     * 登录失败原因
     */
    private String loginFailReason;
    /**
     * IP归属地
     */
    private String ipLocation;
    /**
     * UA浏览器/设备信息
     */
    private String userAgent;
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 是否异常登录
     */
    private boolean isAbnormal;
}