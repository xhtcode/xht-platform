package com.xht.framework.oauth2.token.response;

import com.xht.framework.common.domain.response.BasicResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * oauth2响应
 *
 * @author xht
 **/
@Setter
@Getter
@AllArgsConstructor
public abstract class AbstractOauth2Response extends BasicResponse {

    /**
     * 响应码
     */
    private int code;

}
