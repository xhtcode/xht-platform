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
 * 用户信息表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_user_profiles")
public class SysUserProfilesEntity extends BasicEntity implements Serializable {

    /**
     * 信息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联用户ID
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
    @TableField(value = "id_card_number")
    private String idCardNumber;

    /**
     * 性别(1-男,2-女,3-其他)
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 出生日期
     */
    @TableField(value = "birth_date")
    private LocalDate birthDate;

    /**
     * 年龄(可计算字段)
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 邮政编码
     */
    @TableField(value = "postal_code")
    private String postalCode;
}