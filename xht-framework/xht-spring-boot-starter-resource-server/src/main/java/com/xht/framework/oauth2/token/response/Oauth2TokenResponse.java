package com.xht.framework.oauth2.token.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xht.framework.core.constant.basic.RConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 ：token返回值
 *
 * @author xht
 **/
@Setter
@Getter
@Schema(description = "token返回值")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Oauth2TokenResponse extends AbstractOauth2Response implements Serializable {

    /**
     * 默认构造函数
     */
    public Oauth2TokenResponse() {
        super(RConstants.SUCCESS);
    }

    /**
     * 授权类型
     */
    @Schema(name = "grant_type", defaultValue = "授权类型")
    @JsonProperty(value = "grant_type")
    private String grantType;

    /**
     * 访问令牌
     */
    @Schema(name = "access_token", defaultValue = "访问令牌")
    @JsonProperty(value = "access_token")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @Schema(name = "refresh_token", defaultValue = "刷新令牌")
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    /**
     * 访问令牌类型
     */
    @Schema(name = "token_type", defaultValue = "访问令牌类型")
    @JsonProperty(value = "token_type")
    private String tokenType;

    /**
     * 授权范围
     */
    @Schema(name = "scopes", defaultValue = "授权范围")
    @JsonProperty(value = "scopes")
    private String scopes;

    /**
     * 令牌过期时间
     */
    @Schema(name = "expires_in", defaultValue = "令牌过期时间")
    @JsonProperty(value = "expires_in")
    private Long expiresIn;

    /**
     * 额外的参数
     */
    @Schema(name = "additional", defaultValue = "额外的参数")
    @JsonAnyGetter
    private Map<String, Object> additionalParameters;
}
