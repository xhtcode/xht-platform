package com.xht.boot.security;

import com.xht.framework.core.support.security.UserContextService;
import com.xht.framework.mybatis.mapper.common.UtilsMapper;
import com.xht.framework.oauth2.utils.SecurityUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户上下文服务接口实现
 *
 * @author xht
 **/
@Slf4j
@Service
public class SecurityUserContextServiceImpl implements UserContextService {

    @Resource
    private UtilsMapper utilsMapper;

    /**
     * 获取当前登录用户ID
     *
     * @return 登录用户ID，未登录返回null
     */
    @Override
    public Long userId() {
        return SecurityUtils.getUserId();
    }

    /**
     * 获取当前登录账号（唯一标识，如工号/手机号）
     *
     * @return 登录账号，未登录返回null或空字符串（根据业务约定）
     */
    @Override
    public String userName() {
        return SecurityUtils.getUserName();
    }

    /**
     * 获取当前登录用户名（展示用，如真实姓名）
     *
     * @return 登录用户名，未登录返回null或空字符串
     */
    @Override
    public String nickName() {
        return SecurityUtils.getUser().getNickName();
    }

    /**
     * 获取系统当前时间（Java 8+推荐使用LocalDateTime，无时区问题）
     *
     * @return 当前时间
     */
    @Override
    public LocalDateTime now() {
        return utilsMapper.getCurrentTime();
    }
}
