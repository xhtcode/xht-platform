package com.xht.auth.domain.response;

import com.xht.framework.core.domain.response.IResponse;
import lombok.Data;

/**
 * 验证码返回结果
 *
 * @author xht
 **/
@Data
public class CaptchaResponse implements IResponse {


    private String id;
    /**
     * 验证码信息
     */
    private String captcha;

}
