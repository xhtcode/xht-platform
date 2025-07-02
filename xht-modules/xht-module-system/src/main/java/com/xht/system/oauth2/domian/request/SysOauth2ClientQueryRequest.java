package com.xht.system.oauth2.domian.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * OAuth2客户端管理分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "OAuth2客户端分页查询参数")
public class SysOauth2ClientQueryRequest extends PageQueryRequest {

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    private String clientId;

    /**
     * 客户端名称
     */
    @Schema(description = "客户端名称")
    private String clientName;

}
