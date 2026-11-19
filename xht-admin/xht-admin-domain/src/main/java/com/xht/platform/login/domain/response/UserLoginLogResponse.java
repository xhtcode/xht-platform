package com.xht.platform.login.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.framework.common.enums.LogStatusEnum;
import com.xht.framework.common.enums.LoginTypeEnum;
import com.xht.framework.log.event.LoginRequestInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志
 *
 * @author xht
 */
@Data
@Schema(description = "登录日志响应参数")
public class UserLoginLogResponse extends MetaResponse implements Serializable {

    /**
     * 主键ID
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 链路追踪ID
     */
    @Schema(description = "链路追踪ID")
    private String traceId;

    /**
     * 应用名称（英文标识）
     */
    @Schema(description = "应用名称（英文标识）")
    private String applicationName;

    /**
     * 应用名称（中文名称）
     */
    @Schema(description = "应用名称（中文名称）")
    private String appName;

    /**
     * 登录账号
     */
    @Schema(description = "登录账号")
    private String userName;

    /**
     * 登录类型：PASSWORD-密码登录 PHONE-手机号登录
     */
    @Schema(description = "登录类型")
    private LoginTypeEnum loginType;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    private String loginIp;

    /**
     * 浏览器/客户端标识
     */
    @Schema(description = "浏览器/客户端标识")
    private String userAgent;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    /**
     * 登录状态：0-失败 1-成功
     */
    @Schema(description = "登录状态")
    private LogStatusEnum loginStatus;

    /**
     * 登录失败原因
     */
    @Schema(description = "登录失败原因")
    private String loginFailReason;

    /**
     * 登录请求信息（参数+请求头+请求体）
     */
    @Schema(description = "登录请求信息（参数+请求头+请求体）")
    private LoginRequestInfo loginRequestInfo;

    /**
     * 登录响应信息
     */
    @Schema(description = "登录响应信息")
    private Object loginResponseInfo;

}
