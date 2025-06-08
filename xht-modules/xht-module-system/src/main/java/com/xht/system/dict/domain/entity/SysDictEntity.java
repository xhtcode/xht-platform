package com.xht.system.dict.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.system.dict.common.enums.DictStatusEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统字典表
 *
 * @author xht
 */
@TableName(value = "sys_dict")
@Data
public class SysDictEntity extends BasicEntity implements Serializable {

    /**
     * 字典ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典编码
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @TableField(value = "dict_name")
    private String dictName;

    /**
     * 排序序号
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 字典描述
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @TableField(value = "status")
    private DictStatusEnums status;

}
