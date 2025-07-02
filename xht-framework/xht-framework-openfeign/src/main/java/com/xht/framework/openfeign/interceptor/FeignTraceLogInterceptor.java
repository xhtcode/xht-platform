package com.xht.framework.openfeign.interceptor;


import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.core.utils.HttpServletUtils;
import com.xht.framework.core.utils.IpUtil;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.log.constat.LogConstant;
import com.xht.framework.log.utils.TraceIdUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;


/**
 * 描述 ：openFeign traceId 拦截器配置
 *
 * @author 小糊涂
 **/
@Slf4j
public class FeignTraceLogInterceptor implements RequestInterceptor {

    private final SecurityHeaderProperties rpcHeaderValue;


    public FeignTraceLogInterceptor(SecurityHeaderProperties rpcHeaderValue) {
        this.rpcHeaderValue = rpcHeaderValue;
        log.debug(">>>>>>openfeign-start 自定义拦截器 <<<<<<");
    }

    public void apply(RequestTemplate template) {
        HttpServletRequest request = HttpServletUtils.getHttpServletRequest();
        if (Objects.isNull(request)) return;
        template.header(rpcHeaderValue.getAuthKey(), rpcHeaderValue.getAuthValue());
        String traceId = request.getHeader(LogConstant.REQUEST_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = TraceIdUtils.generateTraceId();
        }
        template.header(LogConstant.REQUEST_TRACE_ID, traceId);
        template.header(LogConstant.REQUEST_USER_ACCOUNT, request.getHeader(LogConstant.REQUEST_USER_ACCOUNT));
        template.header(LogConstant.REQUEST_USER_ID, request.getHeader(LogConstant.REQUEST_USER_ID));
        template.header("X-Forwarded-For", IpUtil.getIpAddr());
    }

}
