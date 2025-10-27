package com.xht.framework.security.domain.response;

import com.xht.framework.core.domain.dto.BasicDTO;
import com.xht.framework.core.domain.response.BasicResponse;
import lombok.Data;

/**
 * 验证码返回结果
 *
 * @author xht
 **/
@Data
public class CaptchaResponse extends BasicResponse {

    /**
     * 验证码key
     */
    private String key;

    /**
     * 验证码code
     */
    private String code;

}
