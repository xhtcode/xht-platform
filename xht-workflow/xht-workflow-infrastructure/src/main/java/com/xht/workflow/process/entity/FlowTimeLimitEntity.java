package com.xht.workflow.process.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.workflow.process.enums.FlowTimeLimitTypeEnum;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 流程扩展-流程时限
 *
 * @author xht
 */
@Data
@TableName(value = "xht_flow_time_limit")
public class FlowTimeLimitEntity extends BasicEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 业务id
     */
    @TableField(value = "business_id")
    private Long businessId;

}
