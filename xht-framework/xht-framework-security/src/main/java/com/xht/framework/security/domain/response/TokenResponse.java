package com.xht.framework.security.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xht.framework.core.domain.response.IResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 描述 ：token返回值
 *
 * @author xht
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "token返回值")
public class TokenResponse implements IResponse, Serializable {

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
     * 授权范围
     */
    @Schema(name = "scopes", defaultValue = "授权范围")
    @JsonProperty(value = "scopes")
    private Set<String> scopes;

    /**
     * 访问令牌类型
     */
    @Schema(name = "token_type", defaultValue = "访问令牌类型")
    @JsonProperty(value = "token_type")
    private String tokenType;

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
    @JsonProperty(value = "additional")
    private Map<String, Object> additionalParameters;

}
