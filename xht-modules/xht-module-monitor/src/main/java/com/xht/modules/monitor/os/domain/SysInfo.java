package com.xht.modules.monitor.os.domain;

import lombok.Data;

/**
 * 系统基础信息实体
 */
@Data
public class SysInfo {
    /** 主机名 */
    private String hostName;
    /** 主机IP地址 */
    private String hostAddress;
    /** 操作系统名称 */
    private String osName;
    /** 操作系统版本 */
    private String osVersion;
    /** 用户工作目录 */
    private String userDir;
}