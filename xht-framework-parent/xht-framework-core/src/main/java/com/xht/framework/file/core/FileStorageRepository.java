package com.xht.framework.file.core;

import java.io.InputStream;

/**
 * 描述： 文件存储接口
 * <p>
 * 上传能力以 {@link #upload(InputStream, String)} 为核心抽象，其余上传方式均为 default 方法，
 * 统一完成入参校验与格式转换后收敛到核心方法，各存储实现类只需实现核心方法即可获得全部上传能力；
 * 下载能力以 {@link #download(String)} 为核心抽象，统一返回文件输入流，流由调用方负责关闭
 *
 * @author xht
 **/
public interface FileStorageRepository {

    /**
     * 上传文件
     *
     * @param inputStream 文件输入流
     * @param filePath    文件路径
     */
    UploadResponse upload(InputStream inputStream, String filePath);

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return 文件输入流，由调用方负责关闭
     */
    InputStream download(String filePath);

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    void remove(String filePath);

    /**
     * 生成文件预览地址
     *
     * @param fileStorageKey 文件存储key
     * @return 文件预览地址
     */
    String generatePreviewUrl(String fileStorageKey);

}
