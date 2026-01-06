package com.xht.modules.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.system.dao.SysUserDetailDao;
import com.xht.modules.system.dao.mapper.SysUserDetailMapper;
import com.xht.modules.system.dao.mapper.SysUserMapper;
import com.xht.modules.system.domain.entity.SysUserDetailEntity;
import com.xht.modules.system.domain.entity.SysUserEntity;
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
     * @param sysUserEntity      用户信息
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
     * @param sysUserEntity      用户信息
     * @param sysUserDetailEntity 用户详细信息
     */
    @Override
    public void updateUserInfo(SysUserEntity sysUserEntity, SysUserDetailEntity sysUserDetailEntity) {
        //@formatter:off
        LambdaUpdateWrapper<SysUserEntity> userWrapper = new LambdaUpdateWrapper<>();
        userWrapper
                .set(SysUserEntity::getNickName, sysUserEntity.getUserPhone())
                .set(SysUserEntity::getUserStatus, sysUserEntity.getUserStatus())
                .set(SysUserEntity::getUserPhone, sysUserEntity.getUserPhone())
                .set(SysUserEntity::getDeptId, sysUserEntity.getDeptId())
                .set(SysUserEntity::getDeptName, sysUserEntity.getDeptName())
                .eq(SysUserEntity::getId, sysUserEntity.getId());
        LambdaUpdateWrapper<SysUserDetailEntity> userAdminWrapper = new LambdaUpdateWrapper<>();
        userAdminWrapper
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
        update(userAdminWrapper);
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
