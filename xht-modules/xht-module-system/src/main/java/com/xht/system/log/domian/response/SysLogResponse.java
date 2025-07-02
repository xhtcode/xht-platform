package com.xht.system.log.domian.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.framework.log.enums.LogStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统日志管理 响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "系统日志响应信息")
public class SysLogResponse extends BasicResponse {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 日志名称
     */
    @Schema(description ="日志名称")
    private String title;

    /**
     * 服务名称
     */
    @Schema(description ="服务名称")
    private String serviceName;

    /**
     * 日志描述
     */
    @Schema(description ="日志描述")
    private String description;

    /**
     * 日志状态
     */
    @Schema(description ="日志状态")
    private LogStatusEnums status;

    /**
     * 操作IP地址
     */
    @Schema(description ="操作IP地址")
    private String remoteAddr;

    /**
     * 用户代理
     */
    @Schema(description ="用户代理")
    private String userAgent;

    /**
     * 请求URI
     */
    @Schema(description ="请求URI")
    private String requestUri;

    /**
     * 操作方式
     */
    @Schema(description ="操作方式")
    private String method;

    /**
     * 操作提交的数据
     */
    @Schema(description ="操作提交的数据")
    private String params;

    /**
     * 执行时间
     */
    @Schema(description ="执行时间")
    private Long time;

    /**
     * 异常信息
     */
    @Schema(description ="异常信息")
    private String exception;
}
