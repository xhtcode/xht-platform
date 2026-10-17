package com.xht.platform.file.thumbnail;

/**
 * 描述： 缩略图生成器
 * <p>
 * 将图片类文件缩放到目标尺寸并统一输出 JPEG；支持同步生成与异步生成（大图场景）
 *
 * @author xht
 **/
public interface ThumbnailGenerator {

    /**
     * 判断是否支持为该后缀的文件生成缩略图
     *
     * @param fileSuffix 文件后缀（不含点）
     * @return true：支持
     */
    boolean supports(String fileSuffix);

    /**
     * 同步生成缩略图
     *
     * @param sourceStoragePath 原图存储路径
     * @param targetStoragePath 缩略图存储路径
     */
    void generate(String sourceStoragePath, String targetStoragePath);

    /**
     * 异步生成缩略图（走全局异步线程池，失败仅记录日志不抛出）
     *
     * @param fileId             文件ID（生成成功后回写缩略图信息）
     * @param sourceStoragePath  原图存储路径
     * @param targetStoragePath  缩略图存储路径
     */
    void generateAsync(Long fileId, String sourceStoragePath, String targetStoragePath);

}
