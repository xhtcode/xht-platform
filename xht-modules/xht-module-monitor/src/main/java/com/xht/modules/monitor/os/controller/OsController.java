package com.xht.modules.monitor.os.controller;

import com.alibaba.nacos.api.model.v2.Result;
import com.xht.framework.core.domain.R;
import com.xht.modules.monitor.os.service.IOsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 系统监控
 *
 * @author xht
 **/
@Slf4j
@RestController
@RequestMapping("/os")
@RequiredArgsConstructor
public class OsController {


    private final IOsService osService;

    /**
     * 获取系统信息
     *
     * @return 系统信息
     */
    @RequestMapping("/serverInfo")
    public R<Map<String, Object>> serverInfo() {
        Map<String, Object> result = new HashMap<>();
        // CPU信息
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        Map<String, Object> cpu = new HashMap<>();
        cpu.put("name", osBean.getName());
        cpu.put("arch", osBean.getArch());
        cpu.put("availableProcessors", osBean.getAvailableProcessors());
        cpu.put("systemLoadAverage", osBean.getSystemLoadAverage());
        result.put("cpu", cpu);
        // 内存信息
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        Map<String, Object> memory = new HashMap<>();
        memory.put("heapInit", formatBytes(heapUsage.getInit()));
        memory.put("heapUsed", formatBytes(heapUsage.getUsed()));
        memory.put("heapMax", formatBytes(heapUsage.getMax()));
        memory.put("heapCommitted", formatBytes(heapUsage.getCommitted()));
        memory.put("nonHeapUsed", formatBytes(nonHeapUsage.getUsed()));
        result.put("memory", memory);
        // JVM信息
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        Map<String, Object> jvm = new HashMap<>();
        jvm.put("name", runtimeBean.getVmName());
        jvm.put("vendor", runtimeBean.getVmVendor());
        jvm.put("version", runtimeBean.getVmVersion());
        jvm.put("specVersion", runtimeBean.getSpecVersion());
        jvm.put("startTime", formatTime(runtimeBean.getStartTime()));
        jvm.put("uptime", formatDuration(runtimeBean.getUptime()));
        result.put("jvm", jvm);
        // 系统信息
        try {
            InetAddress addr = InetAddress.getLocalHost();
            Map<String, Object> sys = new HashMap<>();
            sys.put("hostName", addr.getHostName());
            sys.put("hostAddress", addr.getHostAddress());
            sys.put("osName", System.getProperty("os.name"));
            sys.put("osVersion", System.getProperty("os.version"));
            sys.put("userDir", System.getProperty("user.dir"));
            result.put("sys", sys);
        } catch (Exception e) {
            // ignore
        }
        // 磁盘信息
        List<Map<String, Object>> disks = new ArrayList<>();
        java.io.File[] roots = java.io.File.listRoots();
        for (java.io.File root : roots) {
            Map<String, Object> disk = new HashMap<>();
            disk.put("path", root.getPath());
            disk.put("total", formatBytes(root.getTotalSpace()));
            disk.put("free", formatBytes(root.getFreeSpace()));
            disk.put("usable", formatBytes(root.getUsableSpace()));
            disk.put("usedPercent", root.getTotalSpace() > 0
                    ? String.format("%.2f", (root.getTotalSpace() - root.getFreeSpace()) * 100.0 / root.getTotalSpace())
                    : "0");
            disks.add(disk);
        }
        result.put("disks", disks);

        return R.ok().build(result);
    }

}
