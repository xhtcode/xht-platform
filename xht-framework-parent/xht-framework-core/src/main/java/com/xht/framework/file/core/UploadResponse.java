package com.xht.framework.file.core;

import com.xht.framework.common.domain.response.XhtResponse;
import com.xht.framework.file.enums.StorageType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 描述： 上传文件DTO
 *
 * @author xht
 **/
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class UploadResponse implements XhtResponse {

    /**
     * 存储类型
     */
    private final StorageType storageType;

    /**
     * 文件桶
     */
    private final String bucket;

    /**
     * 文件上传存储路径
     */
    private final String fileStoragePath;

    /**
     * 文件的md5
     */
    private final String fileMd5;

}
