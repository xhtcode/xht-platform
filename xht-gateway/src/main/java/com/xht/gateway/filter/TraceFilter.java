package com.xht.gateway.filter;

import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.log.utils.TraceIdUtils;
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

import static com.xht.framework.log.constat.LogConstant.*;


/**
 * 分布式请求链路过滤器.
 *
 * @author xht
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TraceFilter implements GlobalFilter, Ordered {

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
        return chain.filter(exchange.mutate()
                .request(request.mutate()
                        .header(REQUEST_USER_ACCOUNT, userAccount)
                        .header(REQUEST_USER_ID, userId)
                        .header(REQUEST_TRACE_ID, traceId)
                        .build()).response(response)
                .build()).doFinally(s -> TraceIdUtils.removeTraceId());
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 获取请求路径URL.
     *
     * @param request 请求对象
     * @return 路径URL
     */
    public static String getRequestURL(ServerHttpRequest request) {
        return request.getPath().pathWithinApplication().value();
    }
}
