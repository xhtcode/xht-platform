package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.generate.constant.enums.GenerateStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代码生成器-代码生成历史记录表
 * @author xht
 */
@Data
@TableName(value = "gen_log")
public class GenLogEntity extends BasicEntity implements Serializable {

    /**
     * 历史记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分组id
     */
    @TableField(value = "group_id")
    private Long groupId;

    /**
     * 生成批次号
     */
    @TableField(value = "batch_no")
    private String batchNo;

    /**
     * 生成时间
     */
    @TableField(value = "generate_time")
    private LocalDateTime generateTime;

    /**
     * 生成文件数量
     */
    @TableField(value = "file_count")
    private Long fileCount;

    /**
     * 生成的表ID（逗号分隔）
     */
    @TableField(value = "table_ids")
    private String tableIds;

    /**
     * 生成状态（success/fail）
     */
    @TableField(value = "status")
    private GenerateStatus status;

    /**
     * 错误信息（失败时记录）
     */
    @TableField(value = "error_msg")
    private String errorMsg;

}