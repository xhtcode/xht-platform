package com.xht.gateway.filter;

import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 分布式请求链路过滤器.
 *
 * @author xht
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TraceFilter implements GlobalFilter, Ordered {
    /**
     * 分布式链路 ID
     */
    private final static String REQUEST_TRACE_ID = HttpConstants.Header.TRACE_ID.getValue();

    /**
     * 用户账号
     */
    private final static String REQUEST_USER_ACCOUNT = HttpConstants.Header.USER_ACCOUNT.getValue();

    /**
     * 用户 ID
     */
    private final static String REQUEST_USER_ID = HttpConstants.Header.USER_ID.getValue();

    /**
     * 获取请求路径URL.
     *
     * @param request 请求对象
     * @return 路径 URL
     */
    public static String getRequestURL(ServerHttpRequest request) {
        return request.getPath().pathWithinApplication().value();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String userId = headers.getFirst(REQUEST_USER_ID);
        String userAccount = headers.getFirst(REQUEST_USER_ACCOUNT);
        String traceId = headers.getFirst(REQUEST_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = TraceIdUtils.generateTraceId();
        }
        TraceIdUtils.putTraceId(traceId);
        String requestURL = getRequestURL(request);
        log.debug("请求路径：{}， 用户ID：{}， 用户账号：{}，链路ID：{}", requestURL, userId, userAccount, traceId);
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(REQUEST_TRACE_ID, traceId);
        return chain.filter(exchange.mutate().request(request.mutate().header(REQUEST_USER_ACCOUNT, userAccount).header(REQUEST_USER_ID, userId).header(REQUEST_TRACE_ID, traceId).build()).response(response).build()).doFinally(s -> TraceIdUtils.removeTraceId());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
