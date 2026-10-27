package com.xht.framework.file.core;

import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.exception.FileStorageException;
import com.xht.framework.utils.AssertUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BoundedInputStream;

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
     * 按偏移、长度读取文件片段（断点续传、视频分片播放等 Range 场景）
     * <p>【契约说明】返回的输入流由调用方负责关闭，推荐 try-with-resources，防止文件句柄泄漏</p>
     * <p>默认实现基于 {@link #download(String)} 跳过偏移量后截取指定长度，各存储实现可按自身能力覆盖
     * （如本地存储随机定位、对象存储走 Range 请求）以获得更好的性能</p>
     *
     * @param filePath 文件路径
     * @param offset   起始字节偏移，不能小于 0
     * @param length   需要读取的字节数，必须大于 0；超出文件剩余大小时按实际可读长度返回
     * @return 片段输入流，由调用方负责关闭
     */
    default InputStream downloadRange(String filePath, long offset, long length) {
        AssertUtils.hasText(filePath, BusinessErrorCode.PARAM_ERROR);
        AssertUtils.isTrue(offset >= 0, BusinessErrorCode.PARAM_ERROR, "起始偏移量不能小于0");
        AssertUtils.isTrue(length > 0, BusinessErrorCode.PARAM_ERROR, "读取长度必须大于0");
        InputStream inputStream = download(filePath);
        try {
            // 跳过偏移量之前的内容（不支持的流会退化为读取丢弃）
            IOUtils.skipFully(inputStream, offset);
            return BoundedInputStream.builder()
                    .setInputStream(inputStream)
                    .setMaxCount(length)
                    .get();
        } catch (Exception e) {
            IOUtils.closeQuietly(inputStream);
            throw new FileStorageException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

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
