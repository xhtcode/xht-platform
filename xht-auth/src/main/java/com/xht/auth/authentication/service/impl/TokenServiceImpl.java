package com.xht.auth.authentication.service.impl;

import com.xht.auth.authentication.service.ITokenService;
import com.xht.auth.configuration.properties.XhtOauth2Properties;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.oauth2.token.TokenInfoLightningCache;
import com.xht.framework.oauth2.token.form.TokenForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 令牌服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements ITokenService {

    private final OAuth2AuthorizationService authorizationService;

    private final TokenInfoLightningCache tokenInfoLightningCache;

    private final XhtOauth2Properties xhtOauth2Properties;

    // 线程池（自定义线程池，避免使用默认的ForkJoinPool）
    private static final ExecutorService executor = Executors.newFixedThreadPool(20);

    /**
     * 检查令牌有效性
     *
     * @param token 令牌
     */
    @Override
    public void checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(GlobalErrorStatusCode.TOKEN_EXPIRED);
        }
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        // 如果令牌不存在，返回 401 错误
        if (Objects.isNull(Optional.ofNullable(authorization).map(OAuth2Authorization::getAccessToken).orElse(null))) {
            throw new BusinessException(GlobalErrorStatusCode.TOKEN_EXPIRED);
        }
    }

    /**
     * 删除令牌
     *
     * @param token 令牌
     */
    @Override
    public void removeToken(String token) {
        if (StringUtils.isEmpty(token)) {
            log.warn("token 令牌为空，无法删除!");
            return;
        }
        // 删除 Token 信息缓存
        tokenInfoLightningCache.deleteTokenInfo(token);
        // 根据 Token 查询授权信息
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        // 处理自定义退出事件，保存相关日志
        SpringContextUtils.publishEvent(new LogoutSuccessEvent(new PreAuthenticatedAuthenticationToken(
                "admin","1234456")));
        // 清空 Access Token
        if (Objects.nonNull(authorization)) {
            // 处理自定义退出事件，保存相关日志
            SpringContextUtils.publishEvent(new LogoutSuccessEvent(new PreAuthenticatedAuthenticationToken(
                    authorization.getPrincipalName(), authorization.getRegisteredClientId())));
            authorizationService.remove(authorization);
        }
        List<String> urls = Optional
                .ofNullable(xhtOauth2Properties)
                .map(XhtOauth2Properties::getResourceServer)
                .map(XhtOauth2Properties.ResourceServer::getServerNames)
                .orElseGet(Collections::emptyList);
        String traceId = TraceIdUtils.getTraceId();
        TokenForm tokenForm = new TokenForm();
        tokenForm.setAccessToken(token);
        for (String item : urls) {
            // 异步执行任务
            CompletableFuture.runAsync(() -> {
                try {
                    // TraceIdUtils.putTraceId(traceId);
                    // R<?> block = loadBalancedWebClientBuilder.build().post()
                    //         .uri(String.format("http://%s/internal/token-cache/clear", item))
                    //         .header(HttpConstants.Header.TRACE_ID.getValue(), traceId)
                    //         .bodyValue(tokenForm)
                    //         .retrieve().bodyToMono(R.class).block();
                    // if (!ROptional.of(block).isSuccess()) {
                    //     throw new BusinessException(JsonUtils.toJsonString(block));
                    // }
                } catch (BusinessException e) {
                    log.warn("{} 请求失败：{}", item, e.getMessage());
                } catch (Exception e) {
                    log.error("{} 删除令牌失败：{}", item, e.getMessage());
                }
            }, executor); // 指定自定义线程池
        }
    }
}
