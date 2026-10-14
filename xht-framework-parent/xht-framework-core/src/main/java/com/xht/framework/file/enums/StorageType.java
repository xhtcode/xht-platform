package com.xht.framework.file.enums;

import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 文件存储类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum StorageType implements XhtEnum<String> {
    /**
     * 本地存储
     */
    LOCAL("LOCAL", "本地存储"),
    /**
     * MinIO存储
     */
    MINIO("MINIO", "MinIO存储"),
    /**
     * S3存储
     */
    S3("S3", "S3存储"),
    /**
     * OSS存储
     */
    OSS("OSS", "OSS存储"),
    /**
     * 数据库存储
     */
    DATABASE("DATABASE", "数据库存储");

    private final String value;

    private final String desc;
}
