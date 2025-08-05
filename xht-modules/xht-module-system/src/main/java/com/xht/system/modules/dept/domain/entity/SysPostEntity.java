package com.xht.system.modules.dept.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.core.enums.SystemFlagEnums;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.system.modules.dept.common.enums.DeptPostStatusEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 岗位信息表
 *
 * @author xht
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_dept_post")
public class SysPostEntity extends BasicEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 岗位ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 岗位编码
     */
    @TableField(value = "post_code")
    private String postCode;

    /**
     * 岗位名称
     */
    @TableField(value = "post_name")
    private String postName;

    /**
     * 岗位排序
     */
    @TableField(value = "post_sort")
    private Integer postSort;

    /**
     * 岗位状态（0正常 1停用）
     */
    @TableField(value = "post_status")
    private DeptPostStatusEnums postStatus;

    /**
     * 系统内置
     */
    @TableField(value = "system_flag")
    private SystemFlagEnums systemFlag;

    /**
     * 岗位描述
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 构造函数 数据初始化使用
     *
     * @param postCode   岗位编码
     * @param postName   岗位名称
     * @param postSort   岗位排序
     * @param remark     备注
     * @param systemFlag 系统内置
     */
    public SysPostEntity(String postCode, String postName, Integer postSort, String remark, SystemFlagEnums systemFlag) {
        this.postCode = postCode;
        this.postName = postName;
        this.postSort = postSort;
        this.remark = remark;
        this.postStatus = DeptPostStatusEnums.NORMAL;
        this.systemFlag = systemFlag;
    }
}