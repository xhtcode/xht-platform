package com.xht.platform.file.dao;

import com.xht.framework.file.core.XhtFileBO;
import com.xht.framework.file.exception.FileStorageException;
import com.xht.framework.file.core.AbstractUploadBO;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileUsedEnum;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

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
    <T> SysFileEntity uploadMultipartFile(AbstractUploadBO<T> multipartFileUploadBO);

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
     * 根据文件存储键查询文件
     *
     * @param fileStorageKey 文件存储键
     * @param fileUsed       文件用途
     * @return 文件信息
     */
    default XhtFileBO findFileInfoByFileStorageKey(String fileStorageKey, FileUsedEnum fileUsed, long timeout, TimeUnit timeUnit) {
        AssertUtils.notNull(fileUsed, "文件用途不能为空");
        return findFileInfoByFileStorageKey(fileStorageKey, fileUsed.getValue(), timeout, timeUnit);
    }

    /**
     * 根据文件存储键查询文件
     *
     * @param fileStorageKey 文件存储键
     * @param fileUsed       文件用途
     * @param timeout        超时时间
     * @param timeUnit       时间单位
     * @return 文件信息
     */
    XhtFileBO findFileInfoByFileStorageKey(String fileStorageKey, String fileUsed, long timeout, TimeUnit timeUnit);

    /**
     * 关联文件
     *
     * @param fileStorageKey 文件存储名称
     * @param fileUsed       文件用途
     * @param fileDesc       文件描述
     */
    void associateFile(String fileStorageKey, String fileUsed, String fileDesc);

    /**
     * 根据文件存储key删除文件
     *
     * @param fileStorageKey 文件存储key
     * @throws FileStorageException 文件存储异常
     */
    void removeByFileStorageKey(String fileStorageKey);

    /**
     * 根据用户名删除用户头像
     *
     * @param username          用户名
     * @param newFileStorageKey 新文件存储key
     * @throws FileStorageException 文件存储异常
     */
    void removeUserAvatar(String username, String newFileStorageKey);

}
