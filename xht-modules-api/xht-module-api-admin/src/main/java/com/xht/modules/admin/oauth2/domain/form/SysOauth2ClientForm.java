package com.xht.modules.admin.oauth2.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.core.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * OAuth2客户端管理 表单提交
 *
 * @author xht
 **/
@Data
@Schema(description = "OAuth2客户端表单请求参数")
public class SysOauth2ClientForm extends BasicForm {

    /**
     * ID
     */
    @Null(message = "唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "唯一标识参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "id")
    private Long id;

    /**
     * 客户端标识
     */
    @NotBlank(message = "客户端标识参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 100, message = "客户端标识长度不能超过100", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "客户端标识")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Null(message = "唯一标识必须为空", groups = {Groups.Update.class})
    @NotBlank(message = "客户端密钥参数不合法", groups = {Groups.Create.class})
    @Schema(description = "客户端密钥")
    private String clientSecret;

    /**
     * 客户端过期时间
     * 这里假设客户端过期时间不合法
     */
    @Schema(description = "客户端过期时间")
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端名称
     */
    @NotBlank(message = "客户端名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "客户端名称")
    private String clientName;

    /**
     * 客户认证方式
     */
    @NotNull(message = "客户认证方式参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "客户认证方式")
    private Set<String> clientAuthenticationMethods;


    /**
     * 客户端授权类型
     * 这里假设授权类型不合法且至少包含一个元素
     */
    @NotNull(message = "客户端授权类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "客户端授权类型")
    private Set<String> authorizationGrantTypes;

    /**
     * 授权后重定向 URI
     * 这里假设回调地址不合法且至少包含一个元素
     */
    @Schema(description = "回调地址")
    private Set<String> redirectUris;

    /**
     * 客户端作用域
     * 这里假设作用域不合法且至少包含一个元素
     */
    @NotNull(message = "客户端作用域参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "客户端作用域")
    private Set<String> scopes;


    /**
     * 请求令牌有效时间
     * 这里假设请求令牌有效时间不合法且必须为正数
     */
    @NotNull(message = "请求令牌有效时间参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Positive(message = "请求令牌有效时间必须为正数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "请求令牌有效时间")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效时间
     * 这里假设刷新令牌有效时间不合法且必须为正数
     */
    @NotNull(message = "刷新令牌有效时间参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Positive(message = "刷新令牌有效时间必须为正数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "刷新令牌有效时间")
    private Integer refreshTokenValidity;

    /**
     * 是否自动放行
     */
    @Schema(description = "是否自动放行")
    private Integer autoApprove;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
