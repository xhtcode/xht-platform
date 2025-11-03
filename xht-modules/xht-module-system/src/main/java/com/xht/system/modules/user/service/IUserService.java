package com.xht.system.modules.user.service;

import com.xht.api.system.user.dto.UserInfoDTO;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.system.modules.user.domain.form.SysUserBasicForm;
import com.xht.system.modules.user.domain.form.UpdatePwdFrom;
import com.xht.system.modules.user.domain.query.SysUserBasicQuery;
import com.xht.system.modules.user.domain.response.SysUserResponse;
import com.xht.system.modules.user.domain.vo.SysUserVO;

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
    void create(SysUserBasicForm form);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void removeByUserId(Long userId);

    /**
     * 更新用户信息
     *
     * @param userForm 用户更新请求对象
     */
    void update(SysUserBasicForm userForm);

    /**
     * 根据ID查找用户
     *
     * @param userId 用户ID
     * @return 找到的用户对象，不存在时返回null
     */
    SysUserVO findByUserId(Long userId);

    /**
     * 根据查询条件分页查找用户
     *
     * @param query 用户查询请求对象
     * @return 用户对象分页结果
     */
    PageResponse<SysUserResponse> findPageList(SysUserBasicQuery query);

    /**
     * 重置密码
     *
     * @param userId 用户ID
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
     * @param userId 用户ID
     * @param status 状态
     */
    void updateStatus(Long userId, UserStatusEnums status);

    /**
     * 根据用户名和登录类型获取用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    UserInfoDTO loadUserByUsername(String username, LoginTypeEnums loginType);

}
