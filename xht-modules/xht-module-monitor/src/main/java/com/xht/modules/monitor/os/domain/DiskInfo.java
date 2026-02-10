package com.xht.modules.monitor.os.domain;

import lombok.Data;

/**
 * 磁盘信息实体
 */
@Data
public class DiskInfo {
    /** 磁盘根路径 */
    private String path;
    /** 总空间（格式化后） */
    private String total;
    /** 空闲空间（格式化后） */
    private String free;
    /** 可用空间（格式化后） */
    private String usable;
    /** 使用率（百分比，保留2位小数） */
    private String usedPercent;
}