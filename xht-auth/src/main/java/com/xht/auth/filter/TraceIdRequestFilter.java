package com.xht.auth.filter;

import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 添加请求头traceId
 *
 * @author xht
 **/
public class TraceIdRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = request.getHeader(HttpConstants.Header.TRACE_ID.getValue());
            if (StringUtils.isEmpty(traceId)) {
                traceId = TraceIdUtils.generateTraceId();
                response.setHeader(HttpConstants.Header.TRACE_ID.getValue(), traceId);
            }
            TraceIdUtils.putTraceId(traceId);
            filterChain.doFilter(request, response);
        } finally {
            TraceIdUtils.removeTraceId();
        }
    }
}
