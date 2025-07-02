package com.xht.framework.log.domain.dto;

import com.xht.framework.core.domain.dto.IDto;
import com.xht.framework.log.enums.LogStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 日志信息DTO
 *
 * @author xht
 **/
@Data
@Schema(description = "日志信息DTO")
public class LogDTO implements IDto {


    /**
     * 日志名称
     */
    @Schema(description = "日志名称")
    private String title;

    /**
     * 服务名称
     */
    @Schema(description = "服务名称")
    private String serviceName;

    /**
     * 日志描述
     */
    @Schema(description = "日志描述")
    private String description;

    /**
     * 日志状态
     */
    @Schema(description = "日志状态")
    private LogStatusEnums status;

    /**
     * 操作IP地址
     */
    @Schema(description = "操作ip地址")
    private String remoteAddr;

    /**
     * 用户代理
     */
    @Schema(description = "用户代理")
    private String userAgent;

    /**
     * 请求URI
     */
    @Schema(description = "请求uri")
    private String requestUri;

    /**
     * 操作方式
     */
    @Schema(description = "操作方式")
    private String method;

    /**
     * 操作提交的数据
     */
    @Schema(description = "提交数据")
    private String params;

    /**
     * 执行时间
     */
    @Schema(description = "方法执行时间")
    private Long time;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String exception;

}
