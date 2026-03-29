package com.xht.framework.core.support.blog.dto;

import com.xht.framework.core.domain.dto.BasicDTO;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日志信息DTO
 *
 * @author xht
 **/
@Data
@Schema(description = "日志信息 DTO")
public class BLogDTO extends BasicDTO {

    /**
     * 日志名称
     */
    @Schema(description = "日志名称")
    private String title;

    /**
     * 日志描述
     */
    @Schema(description = "日志描述")
    private String logDesc;

    /**
     * 链路ID（全局唯一，如UUID）
     */
    @Schema(description = "链路ID")
    private String traceId;

    /**
     * 服务名称（如demo-service）
     */
    @Schema(description = "服务名称")
    private String serviceName;

    /**
     * 类名加方法名（如com.demo.controller.UserController.queryUser）
     */
    @Schema(description = "类名加方法名")
    private String classMethod;

    /**
     * 调用参数（GET/POST参数，JSON格式）
     */
    @Schema(description = "调用参数")
    private String requestParams;

    /**
     * 服务器地址（如192.168.100.1）
     */
    @Schema(description = "服务器地址")
    private String serverAddr;

    /**
     * 请求IP（客户端真实IP）
     */
    @Schema(description = "请求IP")
    private String requestIp;

    /**
     * 请求头信息（JSON格式）
     */
    @Schema(description = "请求头信息")
    private String requestHeaders;

    /**
     * 请求账号（未登录为匿名）
     */
    @Schema(description = "请求账号")
    private String requestAccount;

    /**
     * 请求类型（GET/POST/PUT/DELETE等）
     */
    @Schema(description = "请求类型")
    private String requestType;

    /**
     * 执行时间（请求开始时间）
     */
    @Schema(description = "执行时间")
    private LocalDateTime executeTime;

    /**
     * 执行耗时（单位：毫秒）
     */
    @Schema(description = "执行耗时")
    private Long executeCost;

    /**
     * 执行状态（success：成功，fail：失败）
     */
    @Schema(description = "执行状态")
    private LogStatusEnums executeStatus;

    /**
     * 执行异常信息（失败时存储异常堆栈）
     */
    @Schema(description = "执行异常信息")
    private String executeException;

}
