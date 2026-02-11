package com.xht.framework.core.support.message.core;

import com.xht.framework.core.utils.StringUtils;

import java.util.Objects;

/**
 * 描述 ： 消息用户
 *
 * @param userId   用户ID
 * @param userName 用户姓名
 * @author xht
 */
public record MessageUser(Long userId, String userName) {

    public boolean isEmpty() {
        return Objects.isNull(this.userId) || this.userId < 0L || StringUtils.isEmpty(this.userName);
    }

}
