package com.xht.modules.admin.system.service;

import com.xht.modules.admin.system.domain.form.SysUserForm;
import com.xht.modules.admin.system.domain.form.UpdatePwdFrom;
import com.xht.modules.admin.system.domain.query.SysUserQuery;
import com.xht.modules.admin.system.domain.response.SysUserResponse;
import com.xht.modules.admin.system.domain.vo.SysUserVO;
import com.xht.modules.admin.system.domain.vo.UserLoginVo;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author xht
 **/
public interface IUserService {

    /**
     * 用户注册
     *
     * @param form 用户创建请求对象
     */
    void create(SysUserForm form);

    /**
     * 删除用户
     *
     * @param userId 用户 ID
     */
    void removeByUserId(Long userId);

    /**
     * 更新用户信息
     *
     * @param userForm 用户更新请求对象
     */
    void update(SysUserForm userForm);

    /**
     * 重置密码
     *
     * @param userId 用户 ID
     */
    void resetPassword(Long userId);

    /**
     * 修改密码
     *
     * @param form 用户更新请求对象
     */
    void updatePassword(UpdatePwdFrom form);

    /**
     * 更新用户状态
     *
     * @param userId 用户 ID
     * @param status 状态
     */
    void updateStatus(Long userId, UserStatusEnums status);

    /**
     * 获取当前登录的用户信息
     *
     * @return 用户信息
     */
    default UserLoginVo getUserProfileInfo() {
        BasicUserDetails user = SecurityUtils.getUser();
        UserLoginVo userInfoDTO = loadUserByUsername(user.getUsername(), user.getLoginType());
        userInfoDTO.setPassWord(null);
        userInfoDTO.setPassWordSalt(null);
        return userInfoDTO;
    }

    /**
     * 根据 ID 查找用户
     *
     * @param userId 用户 ID
     * @return 找到的用户对象，不存在时返回null
     */
    SysUserVO findByUserId(Long userId);

    /**
     * 根据查询条件分页查找用户
     *
     * @param query 用户查询请求对象
     * @return 用户对象分页结果
     */
    PageResponse<SysUserResponse> findPageList(SysUserQuery query);

    /**
     * 根据用户名和登录类型获取用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    UserLoginVo loadUserByUsername(String username, LoginTypeEnums loginType);

    /**
     * 获取当前登录用户的路由信息
     *
     * @return 路由信息
     */
    List<INode<Long>> getRouters();

}
