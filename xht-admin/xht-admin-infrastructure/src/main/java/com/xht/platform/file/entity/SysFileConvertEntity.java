package com.xht.platform.file.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 描述： 文件转换任务实体
 * <p>按 file_id + target_format 去重；转换产物有独立过期时间</p>
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file_convert")
public class SysFileConvertEntity extends Entity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 源文件ID
     */
    @TableField(value = "file_id")
    private Long fileId;

    /**
     * 源后缀
     */
    @TableField(value = "source_suffix")
    private String sourceSuffix;

    /**
     * 目标格式
     */
    @TableField(value = "target_format")
    private String targetFormat;

    /**
     * 状态：0待处理 1处理中 2成功 3失败
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 转换产物存储路径
     */
    @TableField(value = "converted_storage_key")
    private String convertedStorageKey;

    /**
     * 转换产物大小
     */
    @TableField(value = "converted_size")
    private Long convertedSize;

    /**
     * 失败原因
     */
    @TableField(value = "error_msg")
    private String errorMsg;

    /**
     * 重试次数
     */
    @TableField(value = "retry_count")
    private Integer retryCount;

    /**
     * 产物过期时间
     */
    @TableField(value = "expire_time")
    private LocalDateTime expireTime;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
