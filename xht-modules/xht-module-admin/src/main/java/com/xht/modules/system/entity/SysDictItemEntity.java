package com.xht.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.api.system.enums.DictStatusEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统字典项表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_dict_item")
public class SysDictItemEntity extends BasicEntity implements Serializable {

    /**
     * 字典项ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属字典ID
     */
    @TableField(value = "dict_id")
    private Long dictId;

    /**
     * 字典项编码
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典项标签
     */
    @TableField(value = "item_label")
    private String itemLabel;

    /**
     * 字典项值
     */
    @TableField(value = "item_value")
    private String itemValue;

    /**
     * 显示颜色
     */
    @TableField(value = "item_color")
    private String itemColor;

    /**
     * 排序序号
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 字典项描述
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @TableField(value = "status")
    private DictStatusEnums status;
}
