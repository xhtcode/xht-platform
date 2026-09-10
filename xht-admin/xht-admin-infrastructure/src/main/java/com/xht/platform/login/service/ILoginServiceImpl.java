package com.xht.platform.login.service;

import com.xht.framework.common.constant.HttpConstants;
import com.xht.framework.common.constant.RConstants;
import com.xht.framework.common.enums.LoginTypeEnum;
import com.xht.framework.jackson.JsonUtils;
import com.xht.framework.oauth2.token.response.Oauth2ErrorResponse;
import com.xht.framework.oauth2.token.response.Oauth2TokenResponse;
import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.security.properties.Oauth2GrantTypeProperties;
import com.xht.framework.security.utils.Oauth2Utils;
import com.xht.platform.login.LoginProperties;
import com.xht.platform.login.converter.LoginConverter;
import com.xht.platform.login.domain.form.PasswordLoginForm;
import com.xht.platform.login.domain.form.PhoneLoginForm;
import com.xht.platform.login.domain.response.LoginResponse;
import com.xht.platform.login.exception.LoginException;
import com.xht.platform.login.manager.LoginManager;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
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

    private final Oauth2GrantTypeProperties oauth2GrantTypeProperties;

    private final LoginManager loginManager;

    private final static LoginExchangeFunction LOGIN_EXCHANGE_FUNCTION = new LoginExchangeFunction();

    /**
     * 表单登录
     *
     * @param servletRequest HTTP请求对象
     * @param passwordLoginForm 表单登录请求参数，包含用户名、密码、验证码等信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    @Override
    public LoginResponse formLogin(HttpServletRequest servletRequest, PasswordLoginForm passwordLoginForm) {
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add(SecurityConstant.REQUEST_USERNAME, passwordLoginForm.getUsername());
        formParams.add(SecurityConstant.REQUEST_PASSWORD, passwordLoginForm.getPassword());
        formParams.add(SecurityConstant.REQUEST_CAPTCHA_CODE_KEY, passwordLoginForm.getCaptchaKey());
        formParams.add(SecurityConstant.REQUEST_CAPTCHA_CODE, passwordLoginForm.getCaptchaCode());
        formParams.add(SecurityConstant.REQUEST_OAUTH2_GRANT_TYPE, LoginTypeEnum.PASSWORD.getValue());
        try {
            Oauth2TokenResponse oauth2TokenResponse = createOauth2Request(formParams);
            loginManager.saveLoginLog(servletRequest, passwordLoginForm.getUsername(), oauth2TokenResponse.getAccessToken(), passwordLoginForm, oauth2TokenResponse);
            return LoginConverter.converter(oauth2TokenResponse);
        } catch (LoginException e) {
            loginManager.saveLoginFailLog(servletRequest, passwordLoginForm.getUsername(), passwordLoginForm, e.getErrorResponse(), e.getMessage());
            log.error("登录失败，响应结果：{}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            loginManager.saveLoginFailLog(servletRequest, passwordLoginForm.getUsername(), passwordLoginForm, null, e.getMessage());
            log.error("登录失败，未知异常：{}", e.getMessage(), e);
            throw new LoginException(e.getMessage());
        }
    }

    /**
     * 手机号登录
     *
     * @param servletRequest HTTP请求对象
     * @param phoneLoginForm 手机号登录请求参数，包含手机号和验证码信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    @Override
    public LoginResponse phoneLogin(HttpServletRequest servletRequest, PhoneLoginForm phoneLoginForm) {
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add(SecurityConstant.REQUEST_PHONE, phoneLoginForm.getPhone());
        formParams.add(SecurityConstant.REQUEST_PHONE_CODE, phoneLoginForm.getPhoneCode());
        formParams.add(SecurityConstant.REQUEST_OAUTH2_GRANT_TYPE, LoginTypeEnum.PHONE.getValue());
        try {
            Oauth2TokenResponse oauth2TokenResponse = createOauth2Request(formParams);
            return LoginConverter.converter(oauth2TokenResponse);
        } catch (LoginException e) {
            loginManager.saveLoginFailLog(servletRequest, phoneLoginForm.getPhone(), phoneLoginForm, e.getErrorResponse(), e.getMessage());
            log.error("登录失败，响应结果：{}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            loginManager.saveLoginFailLog(servletRequest, phoneLoginForm.getPhone(), phoneLoginForm, null, e.getMessage());
            log.error("登录失败，未知异常：{}", e.getMessage(), e);
            throw new LoginException(e.getMessage());
        }
    }


    /**
     * 创建OAuth2请求
     * @param formParams 表单参数
     * @return OAuth2响应对象
     */
    private Oauth2TokenResponse createOauth2Request(MultiValueMap<String, String> formParams) {
        formParams.addAll(OAuth2ParameterNames.SCOPE, loginProperties.getScope());
        formParams.add(SecurityConstant.REQUEST_CUSTOM_GRANT_TYPE, oauth2GrantTypeProperties.getValue());
        String basicAuthorization = Oauth2Utils.assembleBasicAuthorization(loginProperties.getClientId(), loginProperties.getClientSecret());
        Oauth2TokenResponse exchange = restClient.post().uri(loginProperties.getLoginUrl())
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
    static class LoginExchangeFunction implements RestClient.RequestHeadersSpec.ExchangeFunction<Oauth2TokenResponse> {

        @Override
        public Oauth2TokenResponse exchange(@Nonnull HttpRequest clientRequest, @Nonnull RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse clientResponse) throws IOException {
            InputStream body = clientResponse.getBody();
            String result = StreamUtils.copyToString(body, StandardCharsets.UTF_8);
            log.debug("登录响应结果：{}", result);
            Oauth2TokenResponse tokenResponse = JsonUtils.toObject(result, Oauth2TokenResponse.class);
            if (Objects.isNull(tokenResponse)) {
                throw new LoginException("登录失败，未知响应");
            }
            if (Objects.equals(RConstants.SUCCESS, tokenResponse.getCode()) && Objects.equals(HttpStatus.OK, clientResponse.getStatusCode())) {
                return tokenResponse;
            }
            Oauth2ErrorResponse object = JsonUtils.toObject(result, Oauth2ErrorResponse.class);
            throw new LoginException(Objects.requireNonNullElseGet(object, Oauth2ErrorResponse::new));
        }
    }


}
