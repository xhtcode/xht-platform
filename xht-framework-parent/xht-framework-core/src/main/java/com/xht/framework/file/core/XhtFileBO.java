package com.xht.framework.file.core;

import com.xht.framework.common.domain.bo.XhtBO;
import com.xht.framework.file.enums.FileStatusEnum;
import com.xht.framework.file.enums.PreviewTypeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 描述： 系统文件业务对象
 *
 * @author xht
 */
@Getter
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class XhtFileBO implements XhtBO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件原始名称
     */
    private final String fileOriginalName;

    /**
     * 文件大小
     */
    private final Long fileSize;

    /**
     * 文件大小信息
     */
    private final String fileSizeInfo;

    /**
     * 文件hash
     */
    private final String fileHash;

    /**
     * 文件类型
     */
    private final String fileType;

    /**
     * 文件后缀
     */
    private final String fileSuffix;

    /**
     * 文件状态
     */
    private final FileStatusEnum fileStatus;

    /**
     * 文件用途
     */
    private final String fileUsed;

    /**
     * 文件存储key
     */
    private final String fileStorageKey;

    /**
     * 文件存储名称
     */
    private final String fileStorageName;

    /**
     * 文件存储路径
     */
    private final String fileStoragePath;

    /**
     * 文件上传时间
     */
    private final LocalDateTime fileUploadTime;

    /**
     * 文件上传用户
     */
    private final String fileUploadUser;

    /**
     * 文件关联时间（文件关联业务时时间）
     */
    private final LocalDateTime fileAssociateTime;

    /**
     * 文件预览类型
     */
    private final PreviewTypeEnum filePreviewType;

}
