package com.xht.modules.monitor.os.domain;

import lombok.Data;
import java.util.List;

/**
 * 服务器信息汇总实体
 * 包含CPU、内存、JVM、系统、磁盘等所有信息
 */
@Data
public class ServerInfo {
    /** CPU信息 */
    private CpuInfo cpu;
    /** 内存信息 */
    private MemoryInfo memory;
    /** JVM信息 */
    private JvmInfo jvm;
    /** 系统基础信息 */
    private SysInfo sys;
    /** 磁盘信息列表 */
    private List<DiskInfo> disks;
}