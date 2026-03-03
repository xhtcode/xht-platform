package com.xht.modules.admin.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.system.dao.SysUserDetailDao;
import com.xht.modules.admin.system.dao.mapper.SysUserDetailMapper;
import com.xht.modules.admin.system.dao.mapper.SysUserMapper;
import com.xht.modules.admin.system.entity.SysUserDetailEntity;
import com.xht.modules.admin.system.entity.SysUserEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员用户信息
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysUserDetailDaoImpl extends MapperRepositoryImpl<SysUserDetailMapper, SysUserDetailEntity> implements SysUserDetailDao {

    @Resource
    private SysUserMapper sysUserMapper;


    /**
     * 保存用户信息
     *
     * @param sysUserEntity       用户信息
     * @param sysUserDetailEntity 用户详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserInfo(SysUserEntity sysUserEntity, SysUserDetailEntity sysUserDetailEntity) {
        sysUserMapper.insert(sysUserEntity);
        save(sysUserDetailEntity);
    }

    /**
     * 更新用户信息
     *
     * @param sysUserEntity       用户信息
     * @param sysUserDetailEntity 用户详细信息
     */
    @Override
    public void updateUserInfo(SysUserEntity sysUserEntity, SysUserDetailEntity sysUserDetailEntity) {
        //@formatter:off
        LambdaUpdateWrapper<SysUserEntity> userWrapper = new LambdaUpdateWrapper<>();
        userWrapper
                .set(condition(sysUserEntity.getUserType()), SysUserEntity::getUserType, sysUserEntity.getUserType())
                .set(condition(sysUserEntity.getUserName()), SysUserEntity::getUserName, sysUserEntity.getUserName())
                .set(condition(sysUserEntity.getNickName()), SysUserEntity::getNickName, sysUserEntity.getNickName())
                .set(condition(sysUserEntity.getUserStatus()), SysUserEntity::getUserStatus, sysUserEntity.getUserStatus())
                .set(condition(sysUserEntity.getUserPhone()), SysUserEntity::getUserPhone, sysUserEntity.getUserPhone())
                .set(condition(sysUserEntity.getUserAvatar()), SysUserEntity::getUserAvatar, sysUserEntity.getUserAvatar())
                .set(condition(sysUserEntity.getDeptId()), SysUserEntity::getDeptId, sysUserEntity.getDeptId())
                .set(condition(sysUserEntity.getDeptName()), SysUserEntity::getDeptName, sysUserEntity.getDeptName())
                .eq(SysUserEntity::getId, sysUserEntity.getId());
        LambdaUpdateWrapper<SysUserDetailEntity> userDetailUpdateWrapper = new LambdaUpdateWrapper<>();
        userDetailUpdateWrapper
                .set(condition(sysUserDetailEntity.getRealName()), SysUserDetailEntity::getRealName, sysUserDetailEntity.getRealName())
                .set(condition(sysUserDetailEntity.getIdCard()), SysUserDetailEntity::getIdCard, sysUserDetailEntity.getIdCard())
                .set(condition(sysUserDetailEntity.getGender()), SysUserDetailEntity::getGender, sysUserDetailEntity.getGender())
                .set(condition(sysUserDetailEntity.getBirthDate()), SysUserDetailEntity::getBirthDate, sysUserDetailEntity.getBirthDate())
                .set(condition(sysUserDetailEntity.getAge()), SysUserDetailEntity::getAge, sysUserDetailEntity.getAge())
                .set(condition(sysUserDetailEntity.getAddress()), SysUserDetailEntity::getAddress, sysUserDetailEntity.getAddress())
                .set(condition(sysUserDetailEntity.getEmergencyContact()), SysUserDetailEntity::getEmergencyContact, sysUserDetailEntity.getEmergencyContact())
                .set(condition(sysUserDetailEntity.getEmergencyPhone()), SysUserDetailEntity::getEmergencyPhone, sysUserDetailEntity.getEmergencyPhone())
                .set(condition(sysUserDetailEntity.getNation()), SysUserDetailEntity::getNation, sysUserDetailEntity.getNation())
                .eq(SysUserDetailEntity::getUserId, sysUserEntity.getId());
        //@formatter:on
        update(userDetailUpdateWrapper);
        sysUserMapper.update(userWrapper);
    }

    /**
     * 根据用户ID删除用户信息
     *
     * @param userId 用户ID
     */
    @Override
    public void removeByUserId(Long userId) {
        LambdaQueryWrapper<SysUserDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDetailEntity::getUserId, userId);
        remove(queryWrapper);
    }

    /**
     * 根据身份证号查询用户信息
     *
     * @param idCard 身份证号
     * @param userId 不包括的用户ID
     * @return true：存在；false：不存在
     */
    @Override
    public boolean checkUserIdCardExists(String idCard, Long userId) {
        LambdaQueryWrapper<SysUserDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDetailEntity::getUserId, userId);
        queryWrapper.ne(condition(userId), SysUserDetailEntity::getUserId, userId);
        return SqlHelper.retBool(count(queryWrapper));
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysUserDetailEntity, ?> getFieldId() {
        return SysUserDetailEntity::getUserId;
    }

}
