package com.xht.framework.file.core;

import com.xht.framework.common.domain.bo.XhtBO;
import com.xht.framework.file.enums.FileStatusEnum;
import com.xht.framework.file.enums.PreviewTypeEnum;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 描述： 系统文件业务对象
 *
 * @param fileOriginalName  文件原始名称
 * @param fileSize          文件大小
 * @param fileSizeInfo      文件大小信息
 * @param fileHash          文件hash
 * @param fileType          文件类型
 * @param fileSuffix        文件后缀
 * @param fileStatus        文件状态
 * @param fileUsed          文件用途
 * @param fileStorageKey    文件存储key
 * @param fileStorageName   文件存储名称
 * @param fileStoragePath   文件存储路径
 * @param fileUploadTime    文件上传时间上传时间
 * @param fileUploadUser    文件上传用户
 * @param fileAssociateTime 文件关联时间（文件关联业务时时间）
 * @param filePreviewType   文件预览类型
 * @author xht
 */
@Builder
public record XhtFileBO(String fileOriginalName, Long fileSize, String fileSizeInfo, String fileHash, String fileType,
                        String fileSuffix, FileStatusEnum fileStatus, String fileUsed, String fileStorageKey,
                        String fileStorageName, String fileStoragePath, LocalDateTime fileUploadTime,
                        String fileUploadUser, LocalDateTime fileAssociateTime,
                        PreviewTypeEnum filePreviewType) implements XhtBO {
}
