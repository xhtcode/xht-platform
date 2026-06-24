package com.xht.auth.authentication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.common.enums.UserStatusEnums;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.framework.security.constant.Oauth2BindStatus;
import lombok.Data;

import java.io.Serializable;


/**
 * 描述： 第三方账号实体类
 *
 * @author xht
 */
@Data
@TableName(value = "oauth2_third_account")
public class Oauth2ThirdAccountEntity extends BasicEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 第三方用户唯一标识
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 第三方平台标识
     */
    @TableField(value = "platform_type")
    private String platformType;

    /**
     * 第三方用户昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 第三方用户头像
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 第三方绑定状态
     */
    @TableField(value = "bind_status")
    private Oauth2BindStatus bindStatus;

    /**
     * 明文密码
     */
    @TableField(exist = false)
    private String passWordPlainText;

    /**
     * 用户状态
     */
    @TableField(exist = false)
    private UserStatusEnums userStatus;
}