package com.xht.platform.file.convert;

import java.nio.file.Path;

/**
 * 描述： 文件转换器
 * <p>实现类按源后缀与目标格式声明支持范围，转换过程使用临时文件，异常由执行器统一处理</p>
 *
 * @author xht
 **/
public interface FileConverter {

    /**
     * 是否支持该转换
     *
     * @param sourceSuffix 源后缀
     * @param targetFormat 目标格式：pdf/webp/png
     * @return true：支持
     */
    boolean supports(String sourceSuffix, String targetFormat);

    /**
     * 执行转换
     *
     * @param source       源文件临时路径
     * @param sourceSuffix 源后缀
     * @param target       目标文件临时路径
     * @param targetFormat 目标格式
     * @throws Exception 转换异常
     */
    void convert(Path source, String sourceSuffix, Path target, String targetFormat) throws Exception;

    /**
     * 目标文件后缀
     *
     * @param targetFormat 目标格式
     * @return 文件后缀
     */
    default String targetExtension(String targetFormat) {
        return "webp".equalsIgnoreCase(targetFormat) ? "png" : targetFormat.toLowerCase();
    }

}
