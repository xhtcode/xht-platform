package com.xht.framework.security.constant;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：oauth2 第三方绑定状态
 * 0 绑定
 * 1 未绑定
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum Oauth2BindStatus implements IEnum<Integer> {

    BIND(0, "绑定"),

    NOT_BIND(1, "未绑定");

    private final Integer value;
    private final String desc;
}
