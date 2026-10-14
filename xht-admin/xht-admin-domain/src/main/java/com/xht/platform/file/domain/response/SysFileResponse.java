package com.xht.platform.file.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.platform.file.FileMetadata;
import com.xht.platform.file.enums.FileStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 描述： 系统文件响应实体
 * <p>
 * 继承 {@link MetaResponse} 携带创建/更新时间与创建/更新用户等公共信息
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统文件响应实体")
public class SysFileResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @Schema(description = "文件ID")
    private Long id;

    /**
     * 文件原始名称
     */
    @Schema(description = "文件原始名称")
    private String fileOriginalName;

    /**
     * 文件大小（单位：字节）
     */
    @Schema(description = "文件大小（单位：字节）")
    private Long fileSize;

    /**
     * 文件大小信息
     */
    @Schema(description = "文件大小信息")
    private String fileSizeInfo;

    /**
     * 文件hash
     */
    @Schema(description = "文件hash")
    private String fileHash;

    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String fileType;

    /**
     * 文件后缀
     */
    @Schema(description = "文件后缀")
    private String fileSuffix;

    /**
     * 文件状态
     */
    @Schema(description = "文件状态")
    private FileStatusEnum fileStatus;

    /**
     * 文件用途
     */
    @Schema(description = "文件用途")
    private String fileUsed;

    /**
     * 文件存储类型
     */
    @Schema(description = "文件存储类型")
    private String fileStorageType;

    /**
     * 文件存储名称
     */
    @Schema(description = "文件存储名称")
    private String fileStorageName;

    /**
     * 文件存储key
     */
    @Schema(description = "文件存储key")
    private String fileStorageKey;

    /**
     * 文件存储路径
     */
    @Schema(description = "文件存储路径")
    private String fileStoragePath;

    /**
     * 文件预览地址
     */
    @Schema(description = "文件预览地址")
    private String filePreviewUrl;

    /**
     * 文件元数据（JSON 字符串）
     */
    @Schema(description = "文件元数据（JSON 字符串）")
    private FileMetadata fileMetadata;

    /**
     * 文件上传时间
     */
    @Schema(description = "文件上传时间")
    private LocalDateTime fileUploadTime;

    /**
     * 文件上传用户
     */
    @Schema(description = "文件上传用户")
    private String fileUploadUser;

}
