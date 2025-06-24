package com.xht.framework.log.utils;

import com.xht.framework.log.constat.LogConstant;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * 描述 ：日志 trace 工具类
 *
 * @author 小糊涂
 **/
public final class TraceIdUtils {
    /**
     * 获取 traceId
     *
     * @return traceId
     */
    public static String getTraceId() {
        return MDC.get(LogConstant.MDC_TRACE_ID);
    }

    /**
     * 设置 traceId
     *
     * @param traceId traceId
     */
    public static void putTraceId(String traceId) {
        MDC.put(LogConstant.MDC_TRACE_ID, traceId);
    }

    /**
     * 移除 traceId
     */
    public static void removeTraceId() {
        MDC.remove(LogConstant.MDC_TRACE_ID);
    }

    /**
     * 生成 traceId
     *
     * @return traceId
     */
    public static String generateTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
