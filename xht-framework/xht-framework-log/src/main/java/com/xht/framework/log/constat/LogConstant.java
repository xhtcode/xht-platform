package com.xht.framework.log.constat;

import com.xht.framework.core.constant.HttpConstants;

/**
 * 描述 ：日志常量
 *
 * @author 小糊涂
 **/
public interface LogConstant {

    /**
     * MDC 链路ID
     */
    String MDC_TRACE_ID = "traceId";

    /**
     * 分布式链路ID
     */
    String REQUEST_TRACE_ID = HttpConstants.Header.TRACE_ID.getValue();


    /**
     * 用户账号
     */
    String REQUEST_USER_ACCOUNT = HttpConstants.Header.USER_ACCOUNT.getValue();

    /**
     * 用户ID
     */
    String REQUEST_USER_ID = HttpConstants.Header.USER_ID.getValue();


}
