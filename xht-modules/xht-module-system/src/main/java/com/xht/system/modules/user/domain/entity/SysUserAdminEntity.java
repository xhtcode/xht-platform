package com.xht.system.modules.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 管理员用户信息表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_user_admin")
public class SysUserAdminEntity extends BasicEntity implements Serializable {

    /**
     * 信息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 身份证号
     */
    @TableField(value = "id_card")
    private String idCard;

    /**
     * 用户性别
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 出生日期
     */
    @TableField(value = "birth_date")
    private LocalDate birthDate;

    /**
     * 用户年龄
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 用户地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 邮政编码
     */
    @TableField(value = "postal_code")
    private String postalCode;

    /**
     * 紧急联系人
     */
    @TableField(value = "emergency_contact")
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @TableField(value = "emergency_phone")
    private String emergencyPhone;

    /**
     * 用户民族
     */
    @TableField(value = "nation")
    private String nation;
}