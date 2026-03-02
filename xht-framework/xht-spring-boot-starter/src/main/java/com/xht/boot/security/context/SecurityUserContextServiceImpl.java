package com.xht.boot.security.context;

import com.xht.framework.core.context.UserContextService;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.xht.framework.security.constant.SecurityConstant.DEFAULT_ANONYMITY_USERNAME;

/**
 * 用户上下文服务接口实现
 *
 * @author xht
 **/
@Slf4j
public class SecurityUserContextServiceImpl implements UserContextService {

    /**
     * 获取当前登录用户ID
     *
     * @return 登录用户ID，未登录返回null
     */
    @Override
    public Long userId() {
        return Optional.ofNullable(SecurityUtils.getUser()).map(BasicUserDetails::getUserId).orElse(null);
    }

    /**
     * 获取当前登录账号（唯一标识，如工号/手机号）
     *
     * @return 登录账号，未登录返回null或空字符串（根据业务约定）
     */
    @Override
    public String userName() {
        return Optional.ofNullable(SecurityUtils.getUser()).map(BasicUserDetails::getUsername).orElse(DEFAULT_ANONYMITY_USERNAME);
    }

    /**
     * 获取当前登录用户名（展示用，如真实姓名）
     *
     * @return 登录用户名，未登录返回null或空字符串
     */
    @Override
    public String nickName() {
        return Optional.ofNullable(SecurityUtils.getUser()).map(BasicUserDetails::getNickName).orElse(null);
    }

}
