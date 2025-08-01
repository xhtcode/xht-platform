package com.xht.system.modules.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.system.event.SysUserDeptUpdateEvent;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.entity.SysUserProfilesEntity;
import com.xht.system.modules.user.domain.request.UserQueryRequest;
import com.xht.system.modules.user.domain.vo.SysUserVO;

import java.util.List;

/**
 * @author xht
 **/
public interface SysUserDao extends MapperRepository<SysUserEntity> {

    /**
     * 保存用户信息
     *
     * @param sysUserEntity         用户信息
     * @param sysUserProfilesEntity 用户详细信息
     * @return true：保存成功；false：保存失败
     */
    Boolean saveUserInfo(SysUserEntity sysUserEntity, SysUserProfilesEntity sysUserProfilesEntity, Long postId);

    /**
     * 用户信息删除
     *
     * @param userId 用户ID
     * @return true：删除成功；false：删除失败
     */
    Boolean removeUserInfo(Long userId);

    /**
     * 更新用户信息
     *
     * @param sysUserEntity         用户信息
     * @param sysUserProfilesEntity 用户详细信息
     * @param updateEvent           用户部门更新事件
     * @return true：更新成功；false：更新失败
     */
    Boolean updateUserInfo(SysUserEntity sysUserEntity, SysUserProfilesEntity sysUserProfilesEntity, SysUserDeptUpdateEvent updateEvent);

    /**
     * 更新密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @return true：更新成功；false：更新失败
     */
    Boolean updatePassword(Long userId, String newPassword);

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @return true：更新成功；false：更新失败
     */
    Boolean updateStatus(Long userId, UserStatusEnums status);

    /**
     * 判断身份号是否存在
     *
     * @param idCardNumber 身份号
     * @return true：存在；false：不存在
     */
    Boolean existsIdCardNumber(String idCardNumber);

    /**
     * 根据用户ID查询用户详细信息
     *
     * @param userId 用户ID
     * @return 用户详细信息
     */
    SysUserProfilesEntity findUserProfilesInfo(Long userId);

    /**
     * 分页查询用户信息
     *
     * @param page         分页信息
     * @param queryRequest 查询请求参数
     * @return 分页查询结果
     */
    Page<SysUserVO> queryPageRequest(Page<SysUserEntity> page, UserQueryRequest queryRequest);
    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVO findInfoByUserId(Long userId);

    /**
     * 根据部门ID查询用户简单信息
     *
     * @param leaderUserId 部门领导ID
     * @param deptId       部门ID
     */
    void updateDept(Long leaderUserId, Long deptId);

    /**
     * 判断用户id是否存在
     *
     * @param userIds 用户id列表
     * @return true：存在；false：不存在
     */
    boolean existsByUserId(List<Long> userIds);

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    SysUserVO findByUsernameAndLoginType(String username, LoginTypeEnums loginType);
}
