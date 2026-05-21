package com.xht.auth.authentication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import com.xht.framework.mybatis.domain.entity.DeleteEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

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
     * 链路跟踪ID
     */
    @TableField(value = "trace_id")
    private String traceId;

    /**
     * 登录账号
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 登录类型
     */
    @TableField(value = "login_type")
    private String loginType;

    /**
     * 登录时间
     */
    @TableField(value = "login_time")
    private LocalDateTime loginTime;

    /**
     * 登录IP地址
     */
    @TableField(value = "login_ip")
    private String loginIp;

    /**
     * 浏览器/设备信息
     */
    @TableField(value = "user_agent")
    private String userAgent;

    /**
     * 登录状态：1-成功，0-失败
     */
    @TableField(value = "login_status")
    private LogStatusEnums loginStatus;

    /**
     * 登录信息
     */
    @TableField(value = "login_request_info", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> loginRequestInfo;

    /**
     * 登录失败原因
     */
    @TableField(value = "login_fail_reason")
    private String loginFailReason;

}
