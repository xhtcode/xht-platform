package com.xht.auth.authentication.dao;

import com.xht.modules.admin.oauth2.domain.response.SysOauth2ClientResponse;
import com.xht.modules.admin.system.domain.vo.UserLoginVo;
import com.xht.framework.core.enums.LoginTypeEnums;

import java.util.Set;

/**
 * 认证数据访问接口
 *
 * @author xht
 **/
public interface IAuthenticationDao {

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param userName  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    UserLoginVo findByUsernameAndLoginType(String userName, LoginTypeEnums loginType);

    /**
     * 根据用户ID查询用户拥有的角色编码集合
     *
     * @param userId 用户ID，用于查询对应用户的角色信息
     * @return 返回该用户拥有的所有角色编码的Set集合，如果用户不存在或无角色则返回空集合
     */
    Set<String> findRoleCodesByUserId(Long userId);


    /**
     * 根据用户ID查询用户拥有的菜单编码集合
     *
     * @param userId 用户ID，用于标识要查询的用户
     * @return 包含该用户所有权限菜单编码的Set集合，如果用户不存在或无权限则返回空集合
     */
    Set<String> findMenuCodesByUserId(Long userId);

    /**
     * 根据客户端ID查询客户端信息
     *
     * @param clientId 客户端ID
     * @return 客户端信息
     */
    SysOauth2ClientResponse findClientDetailsById(String clientId);
}
