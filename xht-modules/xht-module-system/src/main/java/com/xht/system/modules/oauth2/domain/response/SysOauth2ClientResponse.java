package com.xht.system.modules.oauth2.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
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
public class SysOauth2ClientResponse extends MetaResponse {

    /**
     * ID
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    private String clientId;

    /**
     * 客户端名称
     */
    @Schema(description = "客户端名称")
    private String clientName;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    private String clientSecret;

    /**
     * 客户端发布时间
     */
    @Schema(description = "客户端发布时间")

    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户端过期时间
     */
    @Schema(description = "客户端过期时间")
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端授权类型
     */
    @Schema(description = "客户端授权类型")
    private Set<String> authorizationGrantTypes;

    /**
     * 客户端作用域
     */
    @Schema(description = "客户端作用域")
    private Set<String> scopes;

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    private Set<String> redirectUris;

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
    private String autoApprove;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
