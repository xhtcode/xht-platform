package com.xht.framework.preview.core.bo;

import com.xht.framework.common.domain.bo.XhtBO;
import com.xht.framework.file.enums.StorageType;
import com.xht.framework.file.enums.PreviewTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * 描述： 文件BO
 *
 * @author xht
 **/
@Getter
@Builder
@AllArgsConstructor
public class FilePreviewBO implements XhtBO {

    /**
     * 文件原始名称
     */
    @NonNull
    private String fileOriginalName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件存储类型
     */
    private StorageType fileStorageType;

    /**
     * 文件存储名称
     */
    private String fileStorageName;

    /**
     * 文件存储路径
     */
    private String fileStoragePath;


    public PreviewTypeEnum previewType;
}
