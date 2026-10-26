package com.xht.platform.file;

import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.core.FileMetadata;

/**
 * 描述： 文件元数据提供者
 *
 * @author xht
 **/
@FunctionalInterface
public interface FileMetadataSupplier<T> {

    /**
     * 获取文件元数据
     *
     * @param uploadBO 上传参数
     * @return 文件元数据
     */
    FileMetadata get(UploadBO<T> uploadBO);
}
