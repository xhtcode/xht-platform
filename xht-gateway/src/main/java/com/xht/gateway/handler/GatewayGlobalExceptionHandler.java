package com.xht.gateway.handler;

import com.xht.framework.core.domain.R;
import com.xht.gateway.utils.WebFluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 描述 ：网关统一异常处理
 *
 * @author : xht
 * @version : 1.0
 **/
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayGlobalExceptionHandler implements ErrorWebExceptionHandler {


    @Override
    @SuppressWarnings("all")
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        R<String> result;
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (ex instanceof NotFoundException) {
            result = R.errorMsg("服务未找到或服务不可用");
        } else if (ex instanceof ResponseStatusException responseStatusException) {
            result = R.errorMsg(responseStatusException.getMessage());
        } else {
            //内部服务器错误
            result = R.errorMsg("内部服务器错误");
        }
        log.debug("[网关异常处理]请求路径:{},异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());
        return WebFluxUtils.webFluxResponseWriter(response, result);
    }
}