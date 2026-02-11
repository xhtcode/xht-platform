package com.xht.framework.core.properties.basic;

import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.support.message.core.MessageUser;

/**
 * 描述 ：基础属性
 *
 * @author xht
 **/
public record MessageProperties(MessageUser admin) {

    public MessageProperties() {
        this(new MessageUser(1L, "admin"));
    }

    public MessageProperties {
        ThrowUtils.notNull(admin, "管理员信息不能为空!");
        ThrowUtils.throwIf(admin.isEmpty(), "管理员信息不能为空!");
    }
}
