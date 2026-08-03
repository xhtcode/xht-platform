package com.xht.workflow.sequence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.workflow.sequence.enums.IsCycleEnums;
import com.xht.workflow.sequence.enums.ResetFlagEnums;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述：流程序列管理
 *
 * @author xht
 **/
@Data
@TableName(value = "xht_flow_sequence")
public class FlowSequenceEntity extends BasicEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 序列id
     */
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private Long id;

    /**
     * 序列编码
     */
    @TableField(value = "sequence_code")
    private String sequenceCode;

    /**
     * 序列名称
     */
    @TableField(value = "sequence_name")
    private String sequenceName;

    /**
     * 序列格式 示例：肥料登记的流水号 032000{YYYYMMDD}-{N} YYYYMMDD,当前日期的格式定义，支持YYYY,YY,MM,DD几种格式组合 {N}原值显示当前值 {N6}当前值显示的最小长度为6位，不足时前面补零
     */
    @TableField(value = "sequence_format")
    private String sequenceFormat;

    /**
     * 序列最小值
     */
    @TableField(value = "min_value")
    private Integer minValue;

    /**
     * 序列最大值
     */
    @TableField(value = "max_value")
    private Integer maxValue;

    /**
     * 序列当前值
     */
    @TableField(value = "current_value")
    private Integer currentValue;

    /**
     * 序列步进值
     */
    @TableField(value = "stepping_value")
    private Integer steppingValue;

    /**
     * 是否循环
     */
    @TableField(value = "is_cycle")
    private IsCycleEnums isCycle;

    /**
     * 重置周期 0 不重置 1每天 2月 3年
     */
    @TableField(value = "reset_flag")
    private ResetFlagEnums resetFlag;

}