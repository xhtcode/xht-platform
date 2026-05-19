package com.xht.auth.consent.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

/**
 * 授权信息响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "授权信息响应信息")
public class SysOauth2AuthorizationConsentResponse extends MetaResponse {

    /**
     *客户端id
     */
    @Schema(description = "客户端id")
    private String registeredClientId;

    /**
     *客户端名称
     */
    @Schema(description = "客户端名称")
    private String registeredClientName;

    /**
     *权限名称
     */
    @Schema(description = "权限名称")
    private String principalName;

    /**
     *授权信息
     */
    @Schema(description = "授权信息")
    private Set<String> authorities;

}
