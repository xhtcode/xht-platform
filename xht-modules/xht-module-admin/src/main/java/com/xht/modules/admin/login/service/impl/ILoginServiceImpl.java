package com.xht.modules.admin.login.service.impl;

import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.constant.basic.RConstants;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.jackson.JsonUtils;
import com.xht.framework.oauth2.token.response.AbstractOauth2Response;
import com.xht.framework.oauth2.token.response.Oauth2ErrorResponse;
import com.xht.framework.oauth2.token.response.Oauth2TokenResponse;
import com.xht.framework.security.utils.Oauth2Utils;
import com.xht.modules.admin.login.LoginProperties;
import com.xht.modules.admin.login.domain.request.FormLoginRequest;
import com.xht.modules.admin.login.domain.request.PhoneLoginRequest;
import com.xht.modules.admin.login.service.ILoginService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 登录服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ILoginServiceImpl implements ILoginService {

    private final RestClient restClient;

    private final LoginProperties loginProperties;

    private final static LoginExchangeFunction LOGIN_EXCHANGE_FUNCTION = new LoginExchangeFunction();

    /**
     * 表单登录
     *
     * @param formLoginRequest 表单登录请求参数，包含用户名、密码、验证码等信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    @Override
    public AbstractOauth2Response formLogin(FormLoginRequest formLoginRequest) {
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("username", formLoginRequest.getUsername());
        formParams.add("password", formLoginRequest.getPassword());
        formParams.add("captcha_key", formLoginRequest.getCaptchaKey());
        formParams.add("captcha_code", formLoginRequest.getCaptchaCode());
        formParams.add("grant_type", LoginTypeEnums.PASSWORD.getValue());
        return createOauth2Request(formParams);
    }

    /**
     * 手机号登录
     *
     * @param phoneLoginRequest 手机号登录请求参数，包含手机号和验证码信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    @Override
    public AbstractOauth2Response phoneLogin(PhoneLoginRequest phoneLoginRequest) {
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("phone", phoneLoginRequest.getPhone());
        formParams.add("phone_code", phoneLoginRequest.getPhoneCode());
        formParams.add("grant_type", LoginTypeEnums.PHONE.getValue());
        return createOauth2Request(formParams);
    }


    /**
     * 创建OAuth2请求
     * @param formParams 表单参数
     * @return OAuth2响应对象
     */
    private AbstractOauth2Response createOauth2Request(MultiValueMap<String, String> formParams) {
        formParams.addAll("scope", loginProperties.getScope());
        String basicAuthorization = Oauth2Utils.assembleBasicAuthorization(loginProperties.getClientId(), loginProperties.getClientSecret());
        AbstractOauth2Response exchange = restClient.post().uri(loginProperties.getLoginUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 表单类型
                .body(formParams)  // 放入表单数据
                .header(HttpConstants.Header.AUTHORIZATION.getValue(), basicAuthorization)
                .exchange(LOGIN_EXCHANGE_FUNCTION);
        log.debug("登录状态：{}", exchange);
        return exchange;
    }

    /**
     * 登录响应处理函数
     *
     * @author xht
     */
    static class LoginExchangeFunction implements RestClient.RequestHeadersSpec.ExchangeFunction<AbstractOauth2Response> {

        @Override
        public AbstractOauth2Response exchange(@Nonnull HttpRequest clientRequest, @Nonnull RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse clientResponse) throws IOException {
            InputStream body = clientResponse.getBody();
            String result = StreamUtils.copyToString(body, StandardCharsets.UTF_8);
            Oauth2TokenResponse tokenResponse = JsonUtils.toObject(result, Oauth2TokenResponse.class);
            if (Objects.isNull(tokenResponse)) {
                return new Oauth2ErrorResponse();
            }
            if (Objects.equals(RConstants.SUCCESS, tokenResponse.getCode())) {
                return tokenResponse;
            }
            return JsonUtils.toObject(result, Oauth2ErrorResponse.class);
        }
    }


}
