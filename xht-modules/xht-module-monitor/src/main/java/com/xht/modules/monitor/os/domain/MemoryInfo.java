package com.xht.modules.monitor.os.domain;

import lombok.Data;

/**
 * 内存信息实体
 */
@Data
public class MemoryInfo {
    /** 堆内存初始值 */
    private String heapInit;
    /** 堆内存已使用 */
    private String heapUsed;
    /** 堆内存最大值 */
    private String heapMax;
    /** 堆内存已提交 */
    private String heapCommitted;
    /** 非堆内存已使用 */
    private String nonHeapUsed;
}