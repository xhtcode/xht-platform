package com.xht.auth.authentication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.xht.framework.core.blog.enums.LogStatusEnums;
import com.xht.framework.log.event.LoginRequestInfo;
import com.xht.framework.mybatis.domain.entity.DeleteEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志
 *
 * @author xht
 */
@Data
@TableName(value = "sys_login_log", autoResultMap = true)
public class SysLoginLogEntity extends DeleteEntity implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 链路追踪ID
     */
    @TableField(value = "trace_id")
    private String traceId;

    /**
     * 应用名称（英文标识）
     */
    @TableField(value = "application_name")
    private String applicationName;

    /**
     * 应用名称（中文名称）
     */
    @TableField(value = "app_name")
    private String appName;

    /**
     * 登录账号
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 登录令牌Token
     */
    @TableField(value = "access_token")
    private String accessToken;

    /**
     * 登录类型：PASSWORD-密码登录 PHONE-手机号登录
     */
    @TableField(value = "login_type")
    private String loginType;

    /**
     * 登录IP地址
     */
    @TableField(value = "login_ip")
    private String loginIp;

    /**
     * 浏览器/客户端标识
     */
    @TableField(value = "user_agent")
    private String userAgent;

    /**
     * 登录时间
     */
    @TableField(value = "login_time")
    private LocalDateTime loginTime;

    /**
     * 登录状态：0-失败 1-成功
     */
    @TableField(value = "login_status")
    private LogStatusEnums loginStatus;

    /**
     * 登录失败原因
     */
    @TableField(value = "login_fail_reason")
    private String loginFailReason;

    /**
     * 登录请求信息（参数+请求头+请求体）
     */
    @TableField(value = "login_request_info", typeHandler = JacksonTypeHandler.class)
    private LoginRequestInfo loginRequestInfo;

    /**
     * 登录响应信息
     */
    @TableField(value = "login_response_info", typeHandler = JacksonTypeHandler.class)
    private Object loginResponseInfo;

}
