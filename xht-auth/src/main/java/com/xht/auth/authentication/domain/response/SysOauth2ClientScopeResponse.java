package com.xht.auth.authentication.domain.response;

import lombok.Data;

/**
 * oauth2 客户端授权范围
 *
 * @author xht
 **/
@Data
public class SysOauth2ClientScopeResponse {

    /**
     * 授权范围ID
     */
    private Long scopeId;

    /**
     * 授权范围名称
     */
    private String scopeName;

    /**
     * 授权范围描述
     */
    private String scopeDesc;

    /**
     * 是否禁用
     */
    private boolean disabled;

    /**
     * 选择状态
     */
    private boolean value;
}
