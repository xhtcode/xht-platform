package com.xht.system.modules.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.domain.entity.SysUserAdminEntity;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.request.SysUserQuery;
import com.xht.system.modules.user.domain.response.UserInfoBasicResponse;
import com.xht.system.modules.user.domain.vo.SysUserVO;


/**
 * @author xht
 **/
public interface SysUserDao extends MapperRepository<SysUserEntity> {

    /**
     * 保存用户信息
     *
     * @param sysUserEntity         用户信息
     * @param sysUserAdminEntity 用户详细信息
     */
    void saveUserInfo(SysUserEntity sysUserEntity, SysUserAdminEntity sysUserAdminEntity);

    /**
     * 用户信息删除
     *
     * @param userId 用户ID
     */
    void removeUserInfo(Long userId);

    /**
     * 更新用户信息
     *
     * @param sysUserEntity         用户信息
     * @param sysUserAdminEntity 用户详细信息
     */
    void updateUserInfo(SysUserEntity sysUserEntity, SysUserAdminEntity sysUserAdminEntity);

    /**
     * 更新密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     */
    void updatePassword(Long userId, String newPassword);

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     */
    void updateStatus(Long userId, UserStatusEnums status);

    /**
     * 分页查询用户信息
     *
     * @param page         分页信息
     * @param query 查询请求参数
     * @return 分页查询结果
     */
    Page<SysUserEntity> queryPageRequest(Page<SysUserEntity> page, SysUserQuery query);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    <T extends UserInfoBasicResponse> SysUserVO<T> findInfoByUserId(Long userId);

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    <T extends UserInfoBasicResponse> SysUserVO<T> findByUsernameAndLoginType(String username, LoginTypeEnums loginType);

    /**
     * 根据手机号和身份证号校验用户是否重复
     *
     * @param neUserId     不包括的用户ID
     * @param phoneNumber  手机号
     * @param idCardNumber 身份证号
     * @return true：存在；false：不存在
     */
    Boolean checkUserRepeat(Long neUserId, String phoneNumber, String idCardNumber);
}
