package com.xht.platform.file.dao;

import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.exception.FileStorageException;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.common.file.bo.UploadBO;
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
    SysFileEntity uploadMultipartFile(UploadBO<MultipartFile> multipartFileUploadBO);

    /**
     * 下载文件
     * <p>【契约说明】返回的输入流由调用方负责关闭，推荐 try-with-resources，防止文件句柄泄漏</p>
     *
     * @param fileStoragePath 文件存储路径
     * @return 文件输入流
     * @throws FileStorageException 文件不存在、读取失败等存储异常
     */
    InputStream download(String fileStoragePath);

}
