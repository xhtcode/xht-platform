package com.xht.workflow.common.exception;

/**
 * 工作流业务 业务异常类
 * 用于封装工作流业务处理过程中的自定义异常
 * @author xht
 */
public class WorkFlowException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 异常消息
     */
    public WorkFlowException(String message) {
        super(message);
    }

    /**
     * 构造函数（包含原因）
     *
     * @param message 异常消息
     * @param cause   异常原因
     */
    public WorkFlowException(String message, Throwable cause) {
        super(message, cause);
    }

}
