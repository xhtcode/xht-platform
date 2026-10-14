package com.xht.framework.file.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import com.xht.framework.utils.StringUtils;
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

    /**
     * 枚举值
     */
    @JsonValue
    private final String value;

    /**
     * 枚举描述
     */
    private final String desc;

    /**
     * 根据枚举值获取枚举对象
     *
     * @param value 枚举值
     * @return 枚举对象
     */
    public static StorageType getByValueIgnoreCase(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        for (StorageType storageType : values()) {
            if (StringUtils.equalsIgnoreCase(storageType.getValue(), value)) {
                return storageType;
            }
        }
        return null;
    }

}
