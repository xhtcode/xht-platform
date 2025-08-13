package com.xht.system.modules.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.dao.mapper.SysUserMapper;
import com.xht.system.modules.user.dao.mapper.SysUserProfilesMapper;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.entity.SysUserProfilesEntity;
import com.xht.system.modules.user.domain.request.UserQueryRequest;
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
    private SysUserProfilesMapper userProfilesMapper;


    /**
     * 保存用户信息
     *
     * @param sysUserEntity         用户信息
     * @param sysUserProfilesEntity 用户详细信息
     * @return true：保存成功；false：保存失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUserInfo(SysUserEntity sysUserEntity, SysUserProfilesEntity sysUserProfilesEntity) {
        save(sysUserEntity);
        sysUserProfilesEntity.setUserId(sysUserEntity.getId());
        userProfilesMapper.insert(sysUserProfilesEntity);
        return Boolean.TRUE;
    }

    /**
     * 用户信息删除
     *
     * @param userId 用户ID
     * @return true：删除成功；false：删除失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeUserInfo(Long userId) {
        LambdaQueryWrapper<SysUserProfilesEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserProfilesEntity::getUserId, userId);
        userProfilesMapper.delete(queryWrapper);
        return removeById(userId);
    }

    /**
     * 更新用户信息
     *
     * @param sysUserEntity         用户信息
     * @param sysUserProfilesEntity 用户详细信息
     * @return true：更新成功；false：更新失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUserInfo(SysUserEntity sysUserEntity, SysUserProfilesEntity sysUserProfilesEntity) {
        //@formatter:off
        LambdaUpdateWrapper<SysUserEntity> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper
                .set(SysUserEntity::getUserType, sysUserEntity.getUserType())
                .set(SysUserEntity::getPhoneNumber, sysUserEntity.getPhoneNumber())
                .set(SysUserEntity::getUserStatus, sysUserEntity.getUserStatus())
                .eq(SysUserEntity::getId, sysUserEntity.getId());
        LambdaUpdateWrapper<SysUserProfilesEntity> userProfilesUpdateWrapper = new LambdaUpdateWrapper<>();
        userProfilesUpdateWrapper
                .set(condition(sysUserProfilesEntity.getRealName()), SysUserProfilesEntity::getRealName, sysUserProfilesEntity.getRealName())
                .set(condition(sysUserProfilesEntity.getIdCardNumber()), SysUserProfilesEntity::getIdCardNumber, sysUserProfilesEntity.getIdCardNumber())
                .set(condition(sysUserProfilesEntity.getGender()), SysUserProfilesEntity::getGender, sysUserProfilesEntity.getGender())
                .set(condition(sysUserProfilesEntity.getBirthDate()), SysUserProfilesEntity::getBirthDate, sysUserProfilesEntity.getBirthDate())
                .set(condition(sysUserProfilesEntity.getAge()), SysUserProfilesEntity::getAge, sysUserProfilesEntity.getAge())
                .set(condition(sysUserProfilesEntity.getAddress()), SysUserProfilesEntity::getAddress, sysUserProfilesEntity.getAddress())
                .set(condition(sysUserProfilesEntity.getPostalCode()), SysUserProfilesEntity::getPostalCode, sysUserProfilesEntity.getPostalCode())
                .eq(SysUserProfilesEntity::getUserId, sysUserEntity.getId());
        //@formatter:on
        update(userUpdateWrapper);
        userProfilesMapper.update(userProfilesUpdateWrapper);
        return true;
    }

    /**
     * 更新密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @return true：更新成功；false：更新失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePassword(Long userId, String newPassword) {
        LambdaUpdateWrapper<SysUserEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(SysUserEntity::getPassWord, newPassword);
        lambdaUpdateWrapper.eq(SysUserEntity::getId, userId);
        return update(lambdaUpdateWrapper);
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @return true：更新成功；false：更新失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(Long userId, UserStatusEnums status) {
        LambdaUpdateWrapper<SysUserEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(SysUserEntity::getUserStatus, status);
        lambdaUpdateWrapper.eq(SysUserEntity::getId, userId);
        return update(lambdaUpdateWrapper);
    }


    /**
     * 分页查询用户信息
     *
     * @param page         分页信息
     * @param queryRequest 查询请求参数
     * @return 分页查询结果
     */
    @Override
    public Page<SysUserVO> queryPageRequest(Page<SysUserEntity> page, UserQueryRequest queryRequest) {
        return baseMapper.queryPageRequest(page, queryRequest);
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public SysUserVO findInfoByUserId(Long userId) {
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
    public SysUserVO findByUsernameAndLoginType(String username, LoginTypeEnums loginType) {
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
