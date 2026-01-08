package com.xht.api.system.feign.fallback;

import com.xht.api.system.domain.vo.UserLoginVo;
import com.xht.api.system.feign.RemoteUserService;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.openfeign.fallback.BasicFallback;

/**
 *
 * @author xht
 **/
public class RemoteUserServiceFallback extends BasicFallback implements RemoteUserService {

    /**
     * 构造函数，创建BasicFallback实例并记录错误日志
     *
     * @param cause 异常原因
     */
    public RemoteUserServiceFallback(Throwable cause) {
        super(cause);
    }

    /**
     * 根据用户名和登录类型获取用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    @Override
    public R<UserLoginVo> loadUserByUsername(String username, LoginTypeEnums loginType) {
        return error();
    }

}
