package com.xht.platform.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.platform.file.FileMetadata;
import com.xht.platform.file.enums.FileStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 系统文件实体
 *
 * @author xht
 **/
@Data
@TableName(value = "sys_file")
public class SysFileEntity extends BasicEntity implements Serializable {

    /**
     * 文件ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件原始名称
     */
    @TableField(value = "file_original_name")
    private String fileOriginalName;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    private Long fileSize;

    /**
     * 文件大小信息
     */
    @TableField(value = "file_size_info")
    private String fileSizeInfo;

    /**
     * 文件hash
     */
    @TableField(value = "file_hash")
    private String fileHash;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 文件后缀
     */
    @TableField(value = "file_suffix")
    private String fileSuffix;

    /**
     * 文件状态
     */
    @TableField(value = "file_status")
    private FileStatusEnum fileStatus;

    /**
     * 文件用途
     */
    @TableField(value = "file_used")
    private String fileUsed;

    /**
     * 文件存储类型
     */
    @TableField(value = "file_storage_type")
    private String fileStorageType;

    /**
     * 文件存储key
     */
    @TableField(value = "file_storage_key")
    private String fileStorageKey;

    /**
     * 文件存储名称
     */
    @TableField(value = "file_storage_name")
    private String fileStorageName;

    /**
     * 文件存储路径
     */
    @TableField(value = "file_storage_path")
    private String fileStoragePath;

    /**
     * 文件预览地址
     */
    @TableField(value = "file_preview_url")
    private String filePreviewUrl;

    /**
     * 文件元数据
     */
    @TableField(value = "file_metadata", typeHandler = JacksonTypeHandler.class)
    private FileMetadata fileMetadata;

    /**
     * 文件上传时间上传时间
     */
    @TableField(value = "file_upload_time")
    private LocalDateTime fileUploadTime;

    /**
     * 文件关联时间（文件关联业务时时间）
     */
    @TableField(value = "file_associate_time")
    private LocalDateTime fileAssociateTime;

    /**
     * 文件上传用户
     */
    @TableField(value = "file_upload_user")
    private String fileUploadUser;

}
