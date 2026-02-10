package com.xht.modules.monitor.os.domain;

import com.xht.framework.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.management.*;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 系统信息控制器
 * 基于实体类封装返回数据，提升类型安全性
 */
@Slf4j
@RestController
public class ServerInfoController {

    // 格式化常量（复用实例，提升性能+保证线程安全）
    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("0.00");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String[] BYTE_UNITS = {"B", "KB", "MB", "GB", "TB"};
    private static final long UNIT_SIZE = 1024L;

    /**
     * 获取系统信息
     *
     * @return 封装后的服务器信息响应
     */
    @RequestMapping("/serverInfo")
    public R<ServerInfo> serverInfo() {
        // 构建顶层实体
        ServerInfo serverInfo = new ServerInfo();
        // 填充各模块信息
        serverInfo.setCpu(getCpuInfo());
        serverInfo.setMemory(getMemoryInfo());
        serverInfo.setJvm(getJvmInfo());
        serverInfo.setSys(getSysInfo());
        serverInfo.setDisks(getDiskInfoList());
        return R.ok().build(serverInfo);
    }

    // ======================== 私有方法：构建各模块实体 ========================

    /**
     * 构建CPU信息实体
     */
    private CpuInfo getCpuInfo() {
        CpuInfo cpuInfo = new CpuInfo();
        OperatingSystemMXBean osMxBean = ManagementFactory.getOperatingSystemMXBean();

        cpuInfo.setName(osMxBean.getName());
        cpuInfo.setArch(osMxBean.getArch());
        cpuInfo.setAvailableProcessors(osMxBean.getAvailableProcessors());
        cpuInfo.setSystemLoadAverage(osMxBean.getSystemLoadAverage());

        return cpuInfo;
    }

    /**
     * 构建内存信息实体
     */
    private MemoryInfo getMemoryInfo() {
        MemoryInfo memoryInfo = new MemoryInfo();
        MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();

        MemoryUsage heapUsage = memoryMxBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryMxBean.getNonHeapMemoryUsage();

        memoryInfo.setHeapInit(formatBytes(heapUsage.getInit()));
        memoryInfo.setHeapUsed(formatBytes(heapUsage.getUsed()));
        memoryInfo.setHeapMax(formatBytes(heapUsage.getMax()));
        memoryInfo.setHeapCommitted(formatBytes(heapUsage.getCommitted()));
        memoryInfo.setNonHeapUsed(formatBytes(nonHeapUsage.getUsed()));

        return memoryInfo;
    }

    /**
     * 构建JVM信息实体
     */
    private JvmInfo getJvmInfo() {
        JvmInfo jvmInfo = new JvmInfo();
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();

        jvmInfo.setName(runtimeMxBean.getVmName());
        jvmInfo.setVendor(runtimeMxBean.getVmVendor());
        jvmInfo.setVersion(runtimeMxBean.getVmVersion());
        jvmInfo.setSpecVersion(runtimeMxBean.getSpecVersion());
        jvmInfo.setStartTime(formatTime(runtimeMxBean.getStartTime()));
        jvmInfo.setUptime(formatDuration(runtimeMxBean.getUptime()));

        return jvmInfo;
    }

    /**
     * 构建系统基础信息实体
     */
    private SysInfo getSysInfo() {
        SysInfo sysInfo = new SysInfo();

        try {
            InetAddress addr = InetAddress.getLocalHost();
            sysInfo.setHostName(addr.getHostName());
            sysInfo.setHostAddress(addr.getHostAddress());
        } catch (Exception e) {
            log.warn("获取主机名/IP失败", e);
            sysInfo.setHostName("获取失败");
            sysInfo.setHostAddress("获取失败");
        }

        // 系统属性获取，添加默认值避免null
        sysInfo.setOsName(Optional.ofNullable(System.getProperty("os.name")).orElse("未知"));
        sysInfo.setOsVersion(Optional.ofNullable(System.getProperty("os.version")).orElse("未知"));
        sysInfo.setUserDir(Optional.ofNullable(System.getProperty("user.dir")).orElse("未知"));

        return sysInfo;
    }

    /**
     * 构建磁盘信息实体列表
     */
    private List<DiskInfo> getDiskInfoList() {
        File[] roots = File.listRoots();
        // 处理listRoots返回null的情况
        if (Objects.isNull(roots)) {
            log.warn("获取磁盘根目录失败：返回null");
            return Collections.emptyList();
        }

        // 流式处理构建磁盘实体列表
        return Arrays.stream(roots)
                .map(root -> {
                    DiskInfo diskInfo = new DiskInfo();
                    try {
                        diskInfo.setPath(root.getPath());
                        diskInfo.setTotal(formatBytes(root.getTotalSpace()));
                        diskInfo.setFree(formatBytes(root.getFreeSpace()));
                        diskInfo.setUsable(formatBytes(root.getUsableSpace()));
                        diskInfo.setUsedPercent(calculateDiskUsedPercent(root));
                    } catch (Exception e) {
                        log.warn("获取磁盘[{}]信息失败", root.getPath(), e);
                    }
                    return diskInfo;
                })
                .collect(Collectors.toList());
    }

    // ======================== 工具方法：格式化/计算逻辑 ========================

    /**
     * 格式化字节数为易读的单位（B/KB/MB/GB/TB）
     */
    private String formatBytes(long bytes) {
        if (bytes <= 0) {
            return "0 B";
        }

        int digitGroups = (int) (Math.log10(bytes) / Math.log10(UNIT_SIZE));
        digitGroups = Math.min(digitGroups, BYTE_UNITS.length - 1);

        double converted = bytes / Math.pow(UNIT_SIZE, digitGroups);
        return String.format("%.2f %s", converted, BYTE_UNITS[digitGroups]);
    }

    /**
     * 格式化时间戳为yyyy-MM-dd HH:mm:ss
     */
    private String formatTime(long timestamp) {
        try {
            synchronized (DATE_FORMAT) { // SimpleDateFormat非线程安全，加锁
                return DATE_FORMAT.format(new Date(timestamp));
            }
        } catch (Exception e) {
            log.warn("格式化时间戳失败：{}", timestamp, e);
            return "格式化失败";
        }
    }

    /**
     * 格式化毫秒数为易读的时长（如：1天2小时30分15秒）
     */
    private String formatDuration(long milliseconds) {
        if (milliseconds < 0) {
            return "0秒";
        }

        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("天");
        }
        if (hours > 0) {
            sb.append(hours).append("小时");
        }
        if (minutes > 0) {
            sb.append(minutes).append("分");
        }
        sb.append(seconds).append("秒");

        return sb.toString();
    }

    /**
     * 计算磁盘使用率（百分比）
     */
    private String calculateDiskUsedPercent(File root) {
        long totalSpace = root.getTotalSpace();
        if (totalSpace <= 0) {
            return "0.00";
        }
        long usedSpace = totalSpace - root.getFreeSpace();
        double usedPercent = (usedSpace * 100.0) / totalSpace;
        synchronized (PERCENT_FORMAT) { // DecimalFormat非线程安全，加锁
            return PERCENT_FORMAT.format(usedPercent);
        }
    }
}