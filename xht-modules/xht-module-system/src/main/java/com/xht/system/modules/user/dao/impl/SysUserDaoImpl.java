package com.xht.system.modules.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.dao.mapper.SysUserAdminMapper;
import com.xht.system.modules.user.dao.mapper.SysUserMapper;
import com.xht.system.modules.user.domain.entity.SysUserAdminEntity;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.request.SysUserQuery;
import com.xht.system.modules.user.domain.response.UserInfoBasicResponse;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xht
 **/
@Slf4j
@Repository
public class SysUserDaoImpl extends MapperRepositoryImpl<SysUserMapper, SysUserEntity> implements SysUserDao {

    @Resource
    private SysUserAdminMapper userProfilesMapper;


    /**
     * 保存用户信息
     *
     * @param sysUserEntity      用户信息
     * @param sysUserAdminEntity 用户详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserInfo(SysUserEntity sysUserEntity, SysUserAdminEntity sysUserAdminEntity) {
        save(sysUserEntity);
        sysUserAdminEntity.setUserId(sysUserEntity.getId());
        userProfilesMapper.insert(sysUserAdminEntity);
    }

    /**
     * 用户信息删除
     *
     * @param userId 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUserInfo(Long userId) {
        LambdaQueryWrapper<SysUserAdminEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserAdminEntity::getUserId, userId);
        userProfilesMapper.delete(queryWrapper);
        removeById(userId);
    }

    /**
     * 更新用户信息
     *
     * @param sysUserEntity      用户信息
     * @param sysUserAdminEntity 用户详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(SysUserEntity sysUserEntity, SysUserAdminEntity sysUserAdminEntity) {
        //@formatter:off
        LambdaUpdateWrapper<SysUserEntity> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper
                .set(SysUserEntity::getNickName, sysUserEntity.getUserPhone())
                .set(SysUserEntity::getUserStatus, sysUserEntity.getUserStatus())
                .set(SysUserEntity::getUserPhone, sysUserEntity.getUserPhone())
                .set(SysUserEntity::getDeptId, sysUserEntity.getDeptId())
                .set(SysUserEntity::getDeptName, sysUserEntity.getDeptName())
                .eq(SysUserEntity::getId, sysUserEntity.getId());
        LambdaUpdateWrapper<SysUserAdminEntity> userProfilesUpdateWrapper = new LambdaUpdateWrapper<>();
        userProfilesUpdateWrapper
                .set(condition(sysUserAdminEntity.getRealName()), SysUserAdminEntity::getRealName, sysUserAdminEntity.getRealName())
                .set(condition(sysUserAdminEntity.getIdCard()), SysUserAdminEntity::getIdCard, sysUserAdminEntity.getIdCard())
                .set(condition(sysUserAdminEntity.getGender()), SysUserAdminEntity::getGender, sysUserAdminEntity.getGender())
                .set(condition(sysUserAdminEntity.getBirthDate()), SysUserAdminEntity::getBirthDate, sysUserAdminEntity.getBirthDate())
                .set(condition(sysUserAdminEntity.getAge()), SysUserAdminEntity::getAge, sysUserAdminEntity.getAge())
                .set(condition(sysUserAdminEntity.getAddress()), SysUserAdminEntity::getAddress, sysUserAdminEntity.getAddress())
                .set(condition(sysUserAdminEntity.getPostalCode()), SysUserAdminEntity::getPostalCode, sysUserAdminEntity.getPostalCode())
                .eq(SysUserAdminEntity::getUserId, sysUserEntity.getId());
        //@formatter:on
        update(userUpdateWrapper);
        userProfilesMapper.update(userProfilesUpdateWrapper);
    }

    /**
     * 更新密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long userId, String newPassword) {
        LambdaUpdateWrapper<SysUserEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(SysUserEntity::getPassWord, newPassword);
        lambdaUpdateWrapper.eq(SysUserEntity::getId, userId);
        update(lambdaUpdateWrapper);
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, UserStatusEnums status) {
        LambdaUpdateWrapper<SysUserEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(SysUserEntity::getUserStatus, status);
        lambdaUpdateWrapper.eq(SysUserEntity::getId, userId);
        update(lambdaUpdateWrapper);
    }

    /**
     * 分页查询用户信息
     *
     * @param page  分页信息
     * @param query 查询请求参数
     * @return 分页查询结果
     */
    @Override
    public Page<SysUserEntity> queryPageRequest(Page<SysUserEntity> page, SysUserQuery query) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = lambdaQueryWrapper();
        return page(page, queryWrapper);
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public <T extends UserInfoBasicResponse> SysUserVO<T> findInfoByUserId(Long userId) {
        return baseMapper.findInfoByUserId(userId);
    }

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    @Override
    public <T extends UserInfoBasicResponse> SysUserVO<T> findByUsernameAndLoginType(String username, LoginTypeEnums loginType) {
        return baseMapper.findByUsernameAndLoginType(username, loginType.getValue());
    }

    /**
     * 根据手机号和身份证号校验用户是否重复
     *
     * @param neUserId     不包括的用户ID
     * @param phoneNumber  手机号
     * @param idCardNumber 身份证号
     * @return true：存在；false：不存在
     */
    @Override
    public Boolean checkUserRepeat(Long neUserId, String phoneNumber, String idCardNumber) {
        return baseMapper.checkUserRepeat(neUserId, phoneNumber, idCardNumber) > 0;
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysUserEntity, ?> getFieldId() {
        return SysUserEntity::getId;
    }
}
