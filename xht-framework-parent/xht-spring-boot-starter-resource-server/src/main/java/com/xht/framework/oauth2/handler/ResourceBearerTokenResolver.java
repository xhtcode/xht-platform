package com.xht.framework.oauth2.handler;

import com.xht.framework.security.properties.PermitAllUrlProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * token 解析器
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
@RequiredArgsConstructor
public class ResourceBearerTokenResolver implements BearerTokenResolver {

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
    private final PermitAllUrlProperties permitAllUrlProperties;
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final boolean allowFormEncodedBodyParameter = false;

    private final boolean allowUriQueryParameter = true;

    private final String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

    /**
     * 解析请求参数中的Bearer Token
     *
     * @param request the request
     * @return the token or null if not found
     */
    private static String resolveFromRequestParameters(HttpServletRequest request) {
        String[] values = request.getParameterValues("access_token");
        if (values == null || values.length == 0) {
            return null;
        }
        if (values.length == 1) {
            return values[0];
        }
        BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
        throw new OAuth2AuthenticationException(error);
    }

    /**
     * 解析请求中的Bearer Token
     *
     * @param request the request
     * @return the token or null if not found
     */
    @Override
    public String resolve(HttpServletRequest request) {
        // 检查请求的URI是否匹配permitAllUrlProperties中配置的任何URL模式
        boolean match = permitAllUrlProperties.getUrls().stream()
                .anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));

        // 如果匹配成功，返回null，表示不需要解析令牌
        if (match) {
            return null;
        }

        // 从请求头中解析Bearer Token
        final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);

        // 检查是否支持从请求参数中解析Bearer Token，并尝试解析
        final String parameterToken = isParameterTokenSupportedForRequest(request)
                ? resolveFromRequestParameters(request)
                : null;

        // 如果请求头中解析到了Bearer Token
        if (authorizationHeaderToken != null) {
            // 如果同时从请求参数中也解析到了Bearer Token，抛出异常
            if (parameterToken != null) {
                final BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
                throw new OAuth2AuthenticationException(error);
            }
            // 返回从请求头中解析到的Bearer Token
            return authorizationHeaderToken;
        }

        // 如果请求参数中解析到了Bearer Token，并且支持参数形式的Token
        if (parameterToken != null && isParameterTokenEnabledForRequest(request)) {
            // 返回从请求参数中解析到的Bearer Token
            return parameterToken;
        }

        // 如果没有解析到任何Bearer Token，返回null
        return null;
    }

    /**
     * 解析请求头中的Bearer Token
     *
     * @param request the request
     * @return the token or null if not found
     */
    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(this.bearerTokenHeaderName);
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group("token");
    }

    /**
     * 是否支持从请求参数中解析Bearer Token
     *
     * @param request the request
     * @return true if supported, false otherwise
     */
    private boolean isParameterTokenSupportedForRequest(final HttpServletRequest request) {
        return (("POST".equals(request.getMethod()) && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType())) || "GET".equals(request.getMethod()));
    }

    /**
     * 是否支持从请求参数中解析Bearer Token
     *
     * @param request the request
     * @return true if enabled, false otherwise
     */
    private boolean isParameterTokenEnabledForRequest(final HttpServletRequest request) {
        return ((this.allowFormEncodedBodyParameter && "POST".equals(request.getMethod()) && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType())) || (this.allowUriQueryParameter && "GET".equals(request.getMethod())));
    }

}
