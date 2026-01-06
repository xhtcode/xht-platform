package com.xht.framework.core.support.security;

import java.time.LocalDateTime;

/**
 * 公共用户上下文接口
 * 统一暴露当前登录用户的核心信息和系统时间
 *
 * @author xht
 */
public interface UserContextService {

    /**
     * 获取当前登录用户ID
     *
     * @return 登录用户ID，未登录返回null
     */
    Long userId();

    /**
     * 获取当前登录账号（唯一标识，如工号/手机号）
     *
     * @return 登录账号，未登录返回null或空字符串（根据业务约定）
     */
    String userName();

    /**
     * 获取当前登录用户名（展示用，如真实姓名）
     *
     * @return 登录用户名，未登录返回null或空字符串
     */
    String nickName();

    /**
     * 获取系统当前时间（Java 8+推荐使用LocalDateTime，无时区问题）
     *
     * @return 当前时间
     */
    LocalDateTime now();

}
