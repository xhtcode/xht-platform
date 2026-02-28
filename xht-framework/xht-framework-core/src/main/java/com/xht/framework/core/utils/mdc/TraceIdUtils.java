package com.xht.framework.core.utils.mdc;

import com.xht.framework.core.utils.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * 描述 ：日志 trace 工具类
 *
 * @author xht
 **/
public final class TraceIdUtils {

    /**
     * MDC 链路ID
     */
    private static final String MDC_TRACE_ID = "traceId";

    /**
     * 获取 traceId
     *
     * @return traceId
     */
    public static String getTraceId() {
        return MDC.get(MDC_TRACE_ID);
    }

    /**
     * 设置 traceId
     *
     * @param traceId traceId
     */
    public static void putTraceId(String traceId) {
        MDC.put(MDC_TRACE_ID, traceId);
    }

    /**
     * 移除 traceId
     */
    public static void removeTraceId() {
        MDC.remove(MDC_TRACE_ID);
    }

    /**
     * 生成 traceId
     *
     * @return traceId
     */
    public static String generateTraceId() {
        String traceId = getTraceId();
        if (StringUtils.hasText(traceId)) {
            return traceId;
        }
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
