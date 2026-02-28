package com.xht.modules.admin.system.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.system.domain.query.SysUserQuery;
import com.xht.modules.admin.system.domain.vo.SysUserVo;
import com.xht.modules.admin.system.entity.SysUserEntity;


/**
 * @author xht
 **/
public interface SysUserDao extends MapperRepository<SysUserEntity> {

    /**
     * 更新密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @param passWordSalt 密码盐
     */
    void updatePassword(Long userId, String newPassword, String passWordSalt);

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
     * @param page  分页信息
     * @param query 查询请求参数
     * @return 分页查询结果
     */
    Page<SysUserEntity> findPageList(Page<SysUserEntity> page, SysUserQuery query);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVo findInfoByUserId(Long userId);

    /**
     * 根据手机号校验用户是否重复
     *
     * @param userPhone 手机号
     * @param neUserId  不包括的用户ID
     * @return true：存在；false：不存在
     */
    boolean checkUserPhoneExists(String userPhone, Long neUserId);

}
