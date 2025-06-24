package com.xht.framework.log.constat;

import com.xht.framework.core.constant.RequestConstant;

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
    String REQUEST_TRACE_ID = RequestConstant.HEADER_TRACE_ID;


    /**
     * 用户账号
     */
    String REQUEST_USER_ACCOUNT = RequestConstant.HEADER_USER_ACCOUNT;

    /**
     * 用户ID
     */
    String REQUEST_USER_ID = RequestConstant.HEADER_USER_ID;


}
