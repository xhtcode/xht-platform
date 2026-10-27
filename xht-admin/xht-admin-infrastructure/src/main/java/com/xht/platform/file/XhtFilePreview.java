package com.xht.platform.file;

/**
 * 描述： 文件预览接口
 *
 * @author xht
 **/
public interface XhtFilePreview {

    /**
     * 是否支持文件预览
     *
     * @param fileSuffix 文件后缀
     * @return 是否支持文件预览
     */
    boolean support(String fileSuffix);

    /**
     * 文件预览处理
     * <p>返回实际用于预览的存储路径：无需转换的实现直接返回原路径；需要转换（如 Office 转 PDF）的实现返回新文件路径，
     * 调用方按文件记录的大小分段读取，因此转换实现需保证返回文件与原文件大小一致，否则应自行维护预览文件元数据</p>
     *
     * @param fileStoragePath 原文件存储路径
     * @return 预览用文件存储路径
     */
    String filePreviewHandle(String fileStoragePath);

}
