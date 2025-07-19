package com.xht.system.modules.log.domian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.log.enums.LogStatusEnums;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统日志
 *
 * @author xht
 **/
@Data
@TableName(value = "sys_log")
public class SysLogEntity extends BasicEntity implements Serializable {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日志名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 服务名称
     */
    @TableField(value = "service_name")
    private String serviceName;

    /**
     * 日志描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 日志状态
     */
    @TableField(value = "status")
    private LogStatusEnums status;

    /**
     * 操作IP地址
     */
    @TableField(value = "remote_addr")
    private String remoteAddr;

    /**
     * 用户代理
     */
    @TableField(value = "user_agent")
    private String userAgent;

    /**
     * 请求URI
     */
    @TableField(value = "request_uri")
    private String requestUri;

    /**
     * 操作方式
     */
    @TableField(value = "method")
    private String method;

    /**
     * 操作提交的数据
     */
    @TableField(value = "params")
    private String params;

    /**
     * 执行时间
     */
    @TableField(value = "time")
    private Long time;

    /**
     * 异常信息
     */
    @TableField(value = "exception")
    private String exception;
}
