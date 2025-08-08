package com.xht.generate.utils;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * Velocity 模板引擎工具类
 * 支持从 classpath 或文件系统加载模板，合并数据生成字符串或文件
 */
public class VelocityUtils {

    // Velocity 引擎实例（线程安全，单例使用）
    private static final VelocityEngine VELOCITY_ENGINE;

    static {
        // 初始化 Velocity 引擎
        VELOCITY_ENGINE = initVelocityEngine();
    }

    /**
     * 私有构造方法，防止实例化
     */
    private VelocityUtils() {
        throw new AssertionError("工具类不允许实例化");
    }

    /**
     * 初始化 Velocity 引擎
     * 支持配置模板加载方式（classpath 或文件系统）、编码等
     */
    private static VelocityEngine initVelocityEngine() {
        Properties props = new Properties();

        // 1. 配置模板加载方式（默认使用 classpath 加载，如需文件系统加载可修改此处）
        // 方式一：classpath 加载（适合 jar 包内的模板）
        props.setProperty("resource.loader", "class");
        props.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());

        // 方式二：文件系统加载（适合外部文件模板，需指定路径）
        // props.setProperty("resource.loader", "file");
        // props.setProperty("file.resource.loader.class", FileResourceLoader.class.getName());
        // props.setProperty("file.resource.loader.path", "/path/to/templates"); // 模板所在目录

        // 2. 配置编码（避免中文乱码）
        props.setProperty(Velocity.INPUT_ENCODING, StandardCharsets.UTF_8.name());

        // 3. 初始化引擎
        VelocityEngine engine = new VelocityEngine();
        engine.init(props);
        return engine;
    }

    /**
     * 将模板与数据合并，返回生成的字符串
     *
     * @param templatePath 模板路径（classpath 下的路径，如 "templates/demo.vm"）
     * @param data         模板中需要的参数（key-value 形式）
     * @return 合并后的字符串
     */
    public static String mergeTemplateToString(String templatePath, Map<String, Object> data) {
        // 创建 Velocity 上下文并放入数据
        VelocityContext context = new VelocityContext();
        if (data != null) {
            data.forEach(context::put);
        }

        // 合并模板到字符串
        try (StringWriter writer = new StringWriter()) {
            // 加载模板（模板路径需与加载方式匹配，classpath 加载时为类路径）
            boolean mergeSuccess = VELOCITY_ENGINE.mergeTemplate(templatePath, 
                    StandardCharsets.UTF_8.name(), context, writer);
            if (!mergeSuccess) {
                throw new RuntimeException("模板合并失败，模板路径：" + templatePath);
            }
            return writer.toString();
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("模板不存在，路径：" + templatePath, e);
        } catch (Exception e) {
            throw new RuntimeException("模板合并异常，模板路径：" + templatePath, e);
        }
    }

    /**
     * 将模板与数据合并，并输出到指定文件
     *
     * @param templatePath  模板路径（classpath 下的路径）
     * @param data          模板参数
     * @param outputFilePath 输出文件路径（如 "D:/output/demo.txt"）
     */
    public static void mergeTemplateToFile(String templatePath, Map<String, Object> data, String outputFilePath) {
        String content = mergeTemplateToString(templatePath, data);
        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(outputFilePath), StandardCharsets.UTF_8))) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("文件写入失败，路径：" + outputFilePath, e);
        }
    }

    /**
     * 手动刷新 Velocity 引擎配置（用于动态修改模板加载路径等场景）
     */
    public static void refreshEngine() {
        // 重新初始化引擎
        synchronized (VelocityUtils.class) {
            // 关闭旧引擎（可选）
            VELOCITY_ENGINE.reset();
            // 重新初始化
            initVelocityEngine();
        }
    }
}