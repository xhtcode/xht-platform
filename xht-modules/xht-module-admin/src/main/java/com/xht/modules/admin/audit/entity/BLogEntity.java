package com.xht.modules.admin.audit.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import com.xht.framework.mybatis.domain.entity.Entity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志表
 *
 * @author xht
 */
@Data
@TableName(value = "b_log")
public class BLogEntity extends Entity implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 日志名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 日志描述
     */
    @TableField(value = "log_desc")
    private String logDesc;

    /**
     * 链路ID（全局唯一，如UUID）
     */
    @TableField(value = "trace_id")
    private String traceId;

    /**
     * 服务名称（如demo-service）
     */
    @TableField(value = "service_name")
    private String serviceName;

    /**
     * 类名加方法名（如com.demo.controller.UserController.queryUser）
     */
    @TableField(value = "class_method")
    private String classMethod;

    /**
     * 调用参数（GET/POST参数，JSON格式）
     */
    @TableField(value = "request_params")
    private String requestParams;

    /**
     * 服务器地址（如192.168.1.100）
     */
    @TableField(value = "server_addr")
    private String serverAddr;

    /**
     * 请求IP（客户端真实IP）
     */
    @TableField(value = "request_ip")
    private String requestIp;

    /**
     * 请求头信息（JSON格式）
     */
    @TableField(value = "request_headers")
    private String requestHeaders;

    /**
     * 请求账号（未登录为匿名）
     */
    @TableField(value = "request_account")
    private String requestAccount;

    /**
     * 请求类型（GET/POST/PUT/DELETE等）
     */
    @TableField(value = "request_type")
    private String requestType;

    /**
     * 执行时间（请求开始时间）
     */
    @TableField(value = "execute_time")
    private LocalDateTime executeTime;

    /**
     * 执行耗时（单位：毫秒）
     */
    @TableField(value = "execute_cost")
    private Long executeCost;

    /**
     * 执行状态（success：成功，fail：失败）
     */
    @TableField(value = "execute_status")
    private LogStatusEnums executeStatus;

    /**
     * 执行异常信息（失败时存储异常堆栈）
     */
    @TableField(value = "execute_exception")
    private String executeException;

}
