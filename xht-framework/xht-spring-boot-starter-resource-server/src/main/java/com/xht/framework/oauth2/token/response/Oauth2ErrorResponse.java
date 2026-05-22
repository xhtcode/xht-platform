package com.xht.framework.oauth2.token.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xht.framework.core.constant.basic.RConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import java.io.Serializable;

/**
 * oauth2错误响应
 *
 * @author xht
 **/
@Getter
@Setter
@Schema(description = "token返回值")
public class Oauth2ErrorResponse extends AbstractOauth2Response implements Serializable {

    public Oauth2ErrorResponse() {
        super(RConstants.FAIL);
    }

    /**
     * 描述信息
     */
    @JsonProperty("msg")
    private String message;

    /**
     * 错误
     */
    @JsonProperty(OAuth2ParameterNames.ERROR)
    private String error;

    /**
     * 错误描述
     */
    @JsonProperty(OAuth2ParameterNames.ERROR_DESCRIPTION)
    private String errorDescription;

    /**
     * 错误uri
     */
    @JsonProperty(OAuth2ParameterNames.ERROR_URI)
    private String errorUri;

}
