package com.xht.system.modules.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.query.SysUserBasicQuery;
import com.xht.system.modules.user.domain.vo.SysUserVO;


/**
 * @author xht
 **/
public interface SysUserDao extends MapperRepository<SysUserEntity> {

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
    Page<SysUserEntity> findPageList(Page<SysUserEntity> page, SysUserBasicQuery query);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVO findInfoByUserId(Long userId);

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    SysUserVO findByUsernameAndLoginType(String username, LoginTypeEnums loginType);

    /**
     * 根据手机号校验用户是否重复
     *
     * @param userPhone 手机号
     * @param neUserId  不包括的用户ID
     * @return true：存在；false：不存在
     */
    boolean checkUserPhoneExists(String userPhone, Long neUserId);

}
