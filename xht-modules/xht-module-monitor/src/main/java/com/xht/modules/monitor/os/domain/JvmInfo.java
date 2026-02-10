package com.xht.modules.monitor.os.domain;

import lombok.Data;

/**
 * JVM信息实体
 */
@Data
public class JvmInfo {
    /** JVM名称 */
    private String name;
    /** JVM厂商 */
    private String vendor;
    /** JVM版本 */
    private String version;
    /** JVM规范版本 */
    private String specVersion;
    /** JVM启动时间（格式化后） */
    private String startTime;
    /** JVM运行时长（格式化后） */
    private String uptime;
}