package com.xht.api.system.user.fallback;

import com.xht.api.system.user.dto.UserInfoDTO;
import com.xht.api.system.user.feign.RemoteUserService;
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
    public R<UserInfoDTO> loadUserByUsername(String username, LoginTypeEnums loginType) {
        return error();
    }

    /**
     * 注册手机用户
     *
     * @param phone 手机号
     * @return 注册用户信息
     */
    @Override
    public R<UserInfoDTO> registerPhoneUser(String phone) {
        return error();
    }

    /**
     * 验证手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    @Override
    public R<Boolean> checkPhoneExists(String phone) {
        return R.errorData(Boolean.FALSE);
    }
}
