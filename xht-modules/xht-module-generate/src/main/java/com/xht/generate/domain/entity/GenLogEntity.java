package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

/**
 * 代码生成器-代码生成历史记录表
 * @TableName gen_log
 */
@TableName(value ="gen_log")
@Data
public class GenLogEntity extends BasicEntity implements Serializable {
    /**
     * 历史记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 生成批次号
     */
    private String batchNo;

    /**
     * 生成时间
     */
    private LocalDateTime generateTime;

    /**
     * 生成文件数量
     */
    private Integer fileCount;

    /**
     * 本地生成路径
     */
    private String generatePath;

    /**
     * 使用的模板ID（逗号分隔）
     */
    private String templateIds;

    /**
     * 生成的表ID（逗号分隔）
     */
    private String tableIds;

    /**
     * 生成状态（success/fail）
     */
    private String status;

    /**
     * 错误信息（失败时记录）
     */
    private String errorMsg;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}