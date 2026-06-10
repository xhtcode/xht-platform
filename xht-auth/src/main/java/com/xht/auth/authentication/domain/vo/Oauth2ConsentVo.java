package com.xht.auth.authentication.domain.vo;

import com.xht.auth.authentication.domain.response.SysOauth2ClientScopeResponse;
import com.xht.framework.common.domain.vo.IVO;
import lombok.Data;

import java.util.List;

/**
 * 授权确认信息 返回参数
 *
 * @author xht
 **/
@Data
public class Oauth2ConsentVo implements IVO {

    /**
     * 账号
     */
    private String username;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 状态参数
     */
    private String state;

    /**
     * 设备码授权流程中的用户码
     */
    private String userCode;

    /**
     * 授权范围
     */
    private List<SysOauth2ClientScopeResponse> scopes;

}
