package com.xht.platform.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.platform.system.enums.RoleStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色类型表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_role_type")
public class SysRoleTypeEntity extends BasicEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 类型编码
     */
    @TableField(value = "type_code")
    private String typeCode;

    /**
     * 类型名称
     */
    @TableField(value = "type_name")
    private String typeName;

    /**
     * 状态（0正常 1停用）
     */
    @TableField(value = "type_status")
    private RoleStatusEnum typeStatus;

    /**
     * 显示顺序
     */
    @TableField(value = "type_sort")
    private Integer typeSort;

    /**
     * 描述
     */
    @TableField(value = "remark")
    private String remark;

}
