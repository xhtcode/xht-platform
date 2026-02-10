package com.xht.modules.monitor.os.domain;

import lombok.Data;

/**
 * CPU信息实体
 */
@Data
public class CpuInfo {
    /** 系统名称（含CPU相关） */
    private String name;
    /** 系统架构 */
    private String arch;
    /** 可用处理器核心数 */
    private int availableProcessors;
    /** 系统负载平均值（Windows返回-1.0） */
    private double systemLoadAverage;
}