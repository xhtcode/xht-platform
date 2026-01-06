package com.xht.framework.openfeign.interceptor;


import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;


/**
 * 描述 ：openFeign traceId 拦截器配置
 *
 * @author xht
 **/
@Slf4j
public class FeignTraceLogInterceptor implements RequestInterceptor {

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


    public void apply(RequestTemplate template) {
        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        if (Objects.isNull(request)) return;
        String traceId = request.getHeader(REQUEST_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = TraceIdUtils.generateTraceId();
        }
        template.header(REQUEST_TRACE_ID, traceId);
        template.header(REQUEST_USER_ACCOUNT, request.getHeader(REQUEST_USER_ACCOUNT));
        template.header(REQUEST_USER_ID, request.getHeader(REQUEST_USER_ID));
        template.header("X-Forwarded-For", IpUtils.getIpAddr());
    }

}
