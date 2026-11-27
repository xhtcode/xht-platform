package com.xht.framework.oauth2.handler;

import com.xht.framework.security.properties.PermitAllUrlProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
@RequiredArgsConstructor
public class ResourceBearerTokenResolver implements BearerTokenResolver {

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
    private final PermitAllUrlProperties permitAllUrlProperties;
    private final PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 解析请求中的Bearer Token
     *
     * @param request the request
     * @return the token or null if not found
     */
    @Override
    public String resolve(HttpServletRequest request) {
        // 检查请求的URI是否匹配permitAllUrlProperties中配置的任何URL模式
        boolean match = permitAllUrlProperties.getEndpoints().stream()
                .anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));
        // 如果匹配成功，返回null，表示不需要解析令牌
        if (match) {
            return null;
        }
        // 从请求头中解析Bearer Token
        return resolveFromAuthorizationHeader(request);
    }

    /**
     * 解析请求头中的Bearer Token
     *
     * @param request the request
     * @return the token or null if not found
     */
    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;
        String authorization = request.getHeader(bearerTokenHeaderName);
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

}
