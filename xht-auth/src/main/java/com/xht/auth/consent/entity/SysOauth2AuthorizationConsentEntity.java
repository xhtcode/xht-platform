package com.xht.auth.consent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.util.Set;

/**
 * 授权确认信息
 * <p>
 * @author xht
 */
@Data
@TableName(value = "sys_oauth2_authorization_consent", autoResultMap = true)
public class SysOauth2AuthorizationConsentEntity extends BasicEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     *客户端id
     */
    @TableField(value = "registered_client_id")
    private String registeredClientId;

    /**
     *客户端名称
     */
    @TableField(value = "registered_client_name")
    private String registeredClientName;

    /**
     *权限名称
     */
    @TableField(value = "principal_name")
    private String principalName;

    /**
     * 设备码
     */
    @TableField(value = "device_code")
    private String deviceCode;

    /**
     *授权信息
     */
    @TableField(value = "authorities", typeHandler = JacksonTypeHandler.class)
    private Set<String> authorities;

}