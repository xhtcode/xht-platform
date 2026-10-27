package com.xht.platform.file.dao;

import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.exception.FileStorageException;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.common.file.FileMetadataBuilder;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.FileMetadataSupplier;
import com.xht.platform.file.entity.SysFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 描述： 文件存储数据访问层
 *
 * @author xht
 **/
public interface FileStorageDao {

    /**
     * 上传文件
     *
     * @param multipartFileUploadBO 上传入参（封装MultipartFile）
     * @return 文件上传响应信息
     * @throws FileStorageException 文件存储异常
     */
    default SysFileEntity uploadMultipartFile(UploadBO<MultipartFile> multipartFileUploadBO) {
        return uploadMultipartFile(multipartFileUploadBO, (uploadBO) -> FileMetadataBuilder
                .builder()
                .fullOriginalFilename(uploadBO.getRequestFile().getOriginalFilename())
                .build());
    }

    /**
     * 上传文件
     *
     * @param multipartFileUploadBO 上传入参（封装MultipartFile）
     * @param fileMetadataSupplier  文件元数据提供者
     * @return 文件上传响应信息
     * @throws FileStorageException 文件存储异常
     */
    SysFileEntity uploadMultipartFile(UploadBO<MultipartFile> multipartFileUploadBO, FileMetadataSupplier<MultipartFile> fileMetadataSupplier);

    /**
     * 下载文件
     * <p>【契约说明】返回的输入流由调用方负责关闭，推荐 try-with-resources，防止文件句柄泄漏</p>
     *
     * @param sysFileEntity 文件实体
     * @return 文件输入流
     * @throws FileStorageException 文件不存在、读取失败等存储异常
     */
    default InputStream download(SysFileEntity sysFileEntity) {
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        return download(sysFileEntity.getFileStoragePath());
    }

    /**
     * 下载文件
     * <p>【契约说明】返回的输入流由调用方负责关闭，推荐 try-with-resources，防止文件句柄泄漏</p>
     *
     * @param fileStoragePath 文件存储路径
     * @return 文件输入流
     * @throws FileStorageException 文件不存在、读取失败等存储异常
     */
    InputStream download(String fileStoragePath);

    /**
     * 按偏移、长度读取文件片段（断点续传、视频分片播放等 Range 场景）
     * <p>【契约说明】返回的输入流由调用方负责关闭，推荐 try-with-resources，防止文件句柄泄漏</p>
     *
     * @param sysFileEntity 文件实体
     * @param offset        起始字节偏移，不能小于 0
     * @param length        需要读取多少字节，必须大于 0；超出文件剩余大小时按实际长度返回
     * @return 片段输入流，由调用方负责关闭
     */
    default InputStream downloadRange(SysFileEntity sysFileEntity, long offset, long length) {
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        return downloadRange(sysFileEntity.getFileStoragePath(), offset, length);
    }

    /**
     * 按偏移、长度读取文件片段（断点续传、视频分片播放等 Range 场景）
     * <p>【契约说明】返回的输入流由调用方负责关闭，推荐 try-with-resources，防止文件句柄泄漏</p>
     *
     * @param fileStoragePath 文件存储路径（{@link SysFileEntity#getFileStoragePath()}）
     * @param offset          起始字节偏移，不能小于 0
     * @param length          需要读取多少字节，必须大于 0；超出文件剩余大小时按实际长度返回
     * @return 片段输入流，由调用方负责关闭
     */
    InputStream downloadRange(String fileStoragePath, long offset, long length);

    /**
     * 关联文件业务（单文件）：标记为已关联（ASSOCIATED）并记录关联时间
     * <p>
     * 禁止使用批处理：每次仅处理一个文件
     *
     * @param fileStorageKey 文件存储key
     * @param fileUsed       文件用途
     * @param fileDesc       文件描述
     */
    void associateFile(String fileStorageKey, String fileUsed, String fileDesc);

    /**
     * 解关联文件业务（单文件）：标记为已解关联（DISASSOCIATED）并记录解关联时间
     * <p>
     * 禁止使用批处理：每次仅处理一个文件
     *
     * @param fileStorageKey 文件存储key
     */
    void disassociateFile(String fileStorageKey);

}
