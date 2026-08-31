package com.xht.workflow.definition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程表单
 *
 * @author xht
 */
@Data
@TableName(value = "xht_flow_form")
public class FlowFormEntity extends BasicEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表单ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 表单名称
     */
    @TableField(value = "form_name")
    private String formName;

    /**
     * 表单内容
     */
    @TableField(value = "form_content")
    private String formContent;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
