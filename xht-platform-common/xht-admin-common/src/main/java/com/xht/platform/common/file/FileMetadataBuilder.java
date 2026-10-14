package com.xht.platform.common.file;

import com.xht.framework.utils.StringUtils;
import com.xht.platform.common.file.core.FileMetadata;

import java.util.Objects;

import static com.xht.platform.common.file.core.FileMetadata.FULL_ORIGINAL_FILENAME_KEY;

/**
 * 描述： 文件元数据构建器
 * <p>
 * 使用示例：
 * <pre>{@code
 * FileMetadata metadata = FileMetadataBuilder.builder()
 *         .fullOriginalFilename("报告.pdf")
 *         .put("customKey", "customValue")
 *         .build();
 * }</pre>
 *
 * @author xht
 **/
public final class FileMetadataBuilder {

    /**
     * 文件元数据
     */
    private final FileMetadata fileMetadata = new FileMetadata();

    private FileMetadataBuilder() {
    }

    /**
     * 创建文件元数据构建器
     *
     * @return 文件元数据构建器
     */
    public static FileMetadataBuilder builder() {
        return new FileMetadataBuilder();
    }

    /**
     * 设置完整原始文件名（原始文件名超长被截断时，用于兜底记录完整名称）
     *
     * @param fullOriginalFilename 完整原始文件名
     * @return FileMetadataBuilder实例，支持链式调用
     */
    public FileMetadataBuilder fullOriginalFilename(String fullOriginalFilename) {
        return put(FULL_ORIGINAL_FILENAME_KEY, fullOriginalFilename);
    }

    /**
     * 设置自定义元数据键值对（键为空、值为 null 时忽略）
     *
     * @param key   元数据键名
     * @param value 元数据值
     * @return FileMetadataBuilder实例，支持链式调用
     */
    public FileMetadataBuilder put(String key, String value) {
        if (StringUtils.hasText(key) && Objects.nonNull(value)) {
            this.fileMetadata.put(key, value);
        }
        return this;
    }

    /**
     * 构建文件元数据
     *
     * @return 文件元数据
     */
    public FileMetadata build() {
        return this.fileMetadata;
    }

}
