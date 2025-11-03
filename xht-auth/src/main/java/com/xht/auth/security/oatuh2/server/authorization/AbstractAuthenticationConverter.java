package com.xht.auth.security.oatuh2.server.authorization;

import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * 描述 ： 抽象 认证转换器
 *
 * @author xht
 **/
public abstract class AbstractAuthenticationConverter implements AuthenticationConverter {

    /**
     * 转换器
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Authentication}
     */
    @Override
    public final Authentication convert(HttpServletRequest request) {
        // 请求链 FilterOrderRegistration
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!StringUtils.equals(getGrantType(), grantType) || !StringUtils.equals(request.getMethod(), HttpConstants.Method.POST.getValue())) {
            return null;
        }
        // 构建请求参数集合
        MultiValueMap<String, String> parameters = getParameters(request);
        List<String> scopes = parameters.get(OAuth2ParameterNames.SCOPE);
        // 判断scopes
        if (CollectionUtils.isEmpty(scopes)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
        }
        // 获取上下文认证信息
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> additionalParameters = new HashMap<>(parameters.size());
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) && !key.equals(OAuth2ParameterNames.CLIENT_ID)) {
                additionalParameters.put(key, value.stream().findFirst());
            }
        });
        return convert(clientPrincipal, new HashSet<>(scopes), additionalParameters);
    }

    private static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            for (String value : values) {
                parameters.add(key, value);
            }
        });
        return parameters;
    }

    /**
     * 子类实现转换.
     *
     * @param authentication       认证参数
     * @param scopes               范围
     * @param additionalParameters 扩展参数
     * @return {@link Authentication}
     */
    protected abstract Authentication convert(Authentication authentication, Set<String> scopes, Map<String, Object> additionalParameters);

    /**
     * 获取认证类型.
     *
     * @return 认证类型
     */
    protected abstract String getGrantType();

}
