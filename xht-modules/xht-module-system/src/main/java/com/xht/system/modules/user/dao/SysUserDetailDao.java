package com.xht.system.modules.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.system.modules.user.domain.entity.SysUserDetailEntity;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.request.SysUserQuery;
import com.xht.system.modules.user.domain.response.SysUserDetailResponse;
import com.xht.system.modules.user.domain.vo.SysUserVO;

/**
 * 管理员用户信息 Dao
 *
 * @author xht
 **/
public interface SysUserDetailDao extends MapperRepository<SysUserDetailEntity> {

    /**
     * 保存用户信息
     *
     * @param sysUserEntity      用户信息
     * @param sysUserDetailEntity 用户详细信息
     */
    void saveUserInfo(SysUserEntity sysUserEntity, SysUserDetailEntity sysUserDetailEntity);

    /**
     * 更新用户信息
     *
     * @param sysUserEntity      用户信息
     * @param sysUserDetailEntity 用户详细信息
     */
    void updateUserInfo(SysUserEntity sysUserEntity, SysUserDetailEntity sysUserDetailEntity);

    /**
     * 根据用户ID删除用户信息
     *
     * @param userId 用户ID
     */
    void removeByUserId(Long userId);

    /**
     * 根据身份证号查询用户信息
     *
     * @param idCard 身份证号
     * @param userId 不包括的用户ID
     * @return true：存在；false：不存在
     */
    boolean checkUserIdCardExists(String idCard, Long userId);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVO<SysUserDetailResponse> findByUserId(Long userId);

    /**
     * 根据用户账号和登录类型获取用户信息
     *
     * @param userName  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    SysUserVO<SysUserDetailResponse> loadUserByUserName(String userName, LoginTypeEnums loginType);

    /**
     * 分页查询用户信息
     *
     * @param page  分页参数
     * @param query 查询参数
     * @return 用户信息
     */
    PageResponse<SysUserVO<SysUserDetailResponse>> findPageList(Page<SysUserDetailEntity> page, SysUserQuery query);
}
