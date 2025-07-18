package com.xht.framework.oauth2.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xht.framework.core.domain.response.IResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

/**
 * @author xht
 **/
@Data
@Schema(description = "OAuth2错误响应")
public class OAuth2ErrorResponse implements IResponse {

    /**
     * 错误码
     */
    @JsonProperty(OAuth2ParameterNames.ERROR)
    private String error;

    /**
     * 错误描述
     */
    @JsonProperty(OAuth2ParameterNames.ERROR_DESCRIPTION)
    private String errorDescription;

    /**
     * 错误URI
     */
    @JsonProperty(OAuth2ParameterNames.ERROR_URI)
    private String errorUri;

}
