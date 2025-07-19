package com.xht.auth.convet;

import com.xht.framework.oauth2.domain.response.OAuth2ErrorResponse;
import com.xht.framework.core.converter.IConverter;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.util.StringUtils;

/**
 * OAuth2Error转换器
 *
 * @author xht
 **/
public final class OAuth2ErrorConvert implements IConverter<OAuth2Error, OAuth2ErrorResponse> {

    /**
     * 转换
     *
     * @param oauth2Error 源对象
     * @return 转换后的对象
     */
    @Override
    public OAuth2ErrorResponse convert(OAuth2Error oauth2Error) {
        OAuth2ErrorResponse parameters = new OAuth2ErrorResponse();
        parameters.setError(oauth2Error.getErrorCode());
        if (StringUtils.hasText(oauth2Error.getDescription())) {
            parameters.setErrorDescription(oauth2Error.getDescription());
        } else {
            parameters.setErrorDescription("unknown error");
        }
        if (StringUtils.hasText(oauth2Error.getUri())) {
            parameters.setErrorUri(oauth2Error.getUri());
        }
        return parameters;
    }

}
