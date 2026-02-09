package com.xht.modules.admin.area.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.DeleteEntity;
import com.xht.modules.admin.area.enums.AreaHasChildEnums;
import lombok.Data;

/**
 * 系统管理 - 行政区划
 *
 * @author xht
 */
@Data
@TableName(value = "sys_area")
public class SysAreaEntity extends DeleteEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 上级
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 区划编码
     */
    @TableField(value = "area_code")
    private String areaCode;

    /**
     * 区划名称
     */
    @TableField(value = "area_name")
    private String areaName;

    /**
     * 邮政编码
     */
    @TableField(value = "area_post_code")
    private String areaPostCode;

    /**
     * 经度
     */
    @TableField(value = "area_longitude")
    private String areaLongitude;

    /**
     * 纬度
     */
    @TableField(value = "area_latitude")
    private String areaLatitude;

    /**
     * 排序
     */
    @TableField(value = "area_sort")
    private Integer areaSort;

    /**
     * 是否存在下级
     */
    @TableField(value = "has_child")
    private AreaHasChildEnums hasChild;

}