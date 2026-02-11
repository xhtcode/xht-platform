package com.xht.auth.authentication.dto;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.framework.oauth2.enums.Oauth2ClientAutoApproveEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * OAuth2客户端管理响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "OAuth2客户端管理响应信息")
public class Oauth2ClientDTO extends BasicResponse {

    /**
     * ID
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 客户端标识
     */
    @Schema(description = "客户端标识")
    private String clientId;

    /**
     * 客户端发布时间
     */
    @Schema(description = "客户端发布时间")
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    private String clientSecret;

    /**
     * 客户端过期时间
     */
    @Schema(description = "客户端过期时间")
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端名称
     */
    @Schema(description = "客户端名称")
    private String clientName;

    /**
     * 客户认证方式
     */
    @Schema(description = "客户认证方式")
    private Set<String> clientAuthenticationMethods;

    /**
     * 客户端授权类型
     */
    @Schema(description = "客户端授权类型")
    private Set<String> authorizationGrantTypes;

    /**
     * 授权后重定向 URI
     */
    @Schema(description = "授权后重定向 URI")
    private Set<String> redirectUris;


    /**
     * 登出后重定向 URI
     */
    @Schema(description = "登出后重定向 URI")
    private Set<String> postLogoutRedirectUris;

    /**
     * 客户端作用域
     */
    @Schema(description = "客户端作用域")
    private Set<String> scopes;

    /**
     * 请求令牌有效时间
     */
    @Schema(description = "请求令牌有效时间")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效时间
     */
    @Schema(description = "刷新令牌有效时间")
    private Integer refreshTokenValidity;

    /**
     * 扩展信息
     */
    @Schema(description = "扩展信息")
    private Map<String, Object> additionalInformation;

    /**
     * 是否自动放行
     */
    @Schema(description = "是否自动放行")
    private Oauth2ClientAutoApproveEnums autoApprove;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
