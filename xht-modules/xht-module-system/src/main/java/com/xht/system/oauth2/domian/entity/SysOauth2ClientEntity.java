package com.xht.system.oauth2.domian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

/**
 * 客户端管理
 *
 * @author xht
 **/
@Data
@TableName(value = "sys_oauth2_client", autoResultMap = true)
public class SysOauth2ClientEntity extends BasicEntity implements Serializable {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户端ID
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 客户端名称
     */
    @TableField(value = "client_name")
    private String clientName;

    /**
     * 客户端密钥
     */
    @TableField(value = "client_secret")
    private String clientSecret;

    /**
     * 客户端发布时间
     */
    @TableField(value = "client_id_issued_at")
    private Instant clientIdIssuedAt;

    /**
     * 客户端过期时间
     */
    @TableField(value = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    /**
     * 客户端授权类型
     */
    @TableField(value = "authorization_grant_types", typeHandler = JacksonTypeHandler.class)
    private Set<String> authorizationGrantTypes;

    /**
     * 客户端作用域
     */
    @TableField(value = "scopes", typeHandler = JacksonTypeHandler.class)
    private Set<String> scopes;

    /**
     * 回调地址
     */
    @TableField(value = "redirect_uris", typeHandler = JacksonTypeHandler.class)
    private Set<String> redirectUris;

    /**
     * 请求令牌有效时间
     */
    @TableField(value = "access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效时间
     */
    @TableField(value = "refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 扩展信息
     */
    @TableField(value = "additional_information", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> additionalInformation;

    /**
     * 是否自动放行
     */
    @TableField(value = "auto_approve")
    private String autoApprove;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
