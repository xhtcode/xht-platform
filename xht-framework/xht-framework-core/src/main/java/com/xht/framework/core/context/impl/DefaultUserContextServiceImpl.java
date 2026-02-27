package com.xht.framework.core.context.impl;

import com.xht.framework.core.context.UserContextService;

import java.time.LocalDateTime;

/**
 * 默认用户上下文服务接口
 *
 * @author xht
 **/
public class DefaultUserContextServiceImpl implements UserContextService {

    /**
     * 获取当前登录用户ID
     *
     * @return 登录用户ID，未登录返回null
     */
    @Override
    public Long userId() {
        return null;
    }

    /**
     * 获取当前登录账号（唯一标识，如工号/手机号）
     *
     * @return 登录账号，未登录返回null或空字符串（根据业务约定）
     */
    @Override
    public String userName() {
        return "anonymity";
    }

    /**
     * 获取当前登录用户名（展示用，如真实姓名）
     *
     * @return 登录用户名，未登录返回null或空字符串
     */
    @Override
    public String nickName() {
        return "anonymity";
    }

    /**
     * 获取系统当前时间（Java 8+推荐使用LocalDateTime，无时区问题）
     *
     * @return 当前时间
     */
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
