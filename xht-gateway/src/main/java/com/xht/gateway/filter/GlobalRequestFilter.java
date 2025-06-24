package com.xht.gateway.filter;

import cn.hutool.core.map.MapUtil;
import com.xht.framework.core.constant.RequestConstant;
import com.xht.framework.core.domain.R;
import com.xht.framework.log.utils.TraceIdUtils;
import com.xht.gateway.config.properties.GatewayGlobalProperties;
import com.xht.gateway.utils.WebFluxUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * 全局请求过滤器
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class GlobalRequestFilter implements GlobalFilter, Ordered {

    private final GatewayGlobalProperties gatewayGlobalProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        Long timeStamp = MapUtil.getLong(queryParams, RequestConstant.PARAM_NOW_TIMESTAMP);
        if (Objects.isNull(timeStamp)) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("非法请求！timeStamp参数不存在！");
            return WebFluxUtils.webFluxResponseWriter(exchange.getResponse(), R.errorMsg("非法请求！"));
        }
        if (!validateRequestTimestamp(timeStamp)) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("非法请求！timeStamp参数已过期！");
            return WebFluxUtils.webFluxResponseWriter(exchange.getResponse(), R.errorMsg("非法请求！"));
        }
        try {
            return chain.filter(exchange);
        } finally {
            TraceIdUtils.removeTraceId();
        }
    }

    private boolean validateRequestTimestamp(Long timeStamp) {
        try {
            // 将时间戳字符串转换为Instant对象
            Instant requestTime = Instant.ofEpochMilli(timeStamp);
            Instant currentTime = Instant.now();
            // 计算时间差（秒）
            long timeDiff = Math.abs(ChronoUnit.SECONDS.between(requestTime, currentTime));
            // 判断是否在有效期内
            return timeDiff <= gatewayGlobalProperties.getTimestampValidPeriod();
        } catch (NumberFormatException e) {
            return false; // 无效的时间戳格式
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}


