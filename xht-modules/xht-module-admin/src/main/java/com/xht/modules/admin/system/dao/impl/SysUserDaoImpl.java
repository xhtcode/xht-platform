package com.xht.modules.admin.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.system.dao.SysUserDao;
import com.xht.modules.admin.system.dao.mapper.SysUserMapper;
import com.xht.modules.admin.system.domain.query.SysUserQuery;
import com.xht.modules.admin.system.domain.vo.SysUserVo;
import com.xht.modules.admin.system.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author xht
 **/
@Slf4j
@Repository
public class SysUserDaoImpl extends MapperRepositoryImpl<SysUserMapper, SysUserEntity> implements SysUserDao {

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
    public Page<SysUserEntity> findPageList(Page<SysUserEntity> page, SysUserQuery query) {
        UserTypeEnums userType = query.getUserType();
        String userName = query.getUserName();
        String nickName = query.getNickName();
        UserStatusEnums userStatus = query.getUserStatus();
        String userPhone = query.getUserPhone();
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(
                condition(query.getKeyWord()), wrapper -> wrapper.or()
                        .like(SysUserEntity::getUserName, query.getKeyWord())
                        .or()
                        .like(SysUserEntity::getNickName, query.getKeyWord())
                        .or()
                        .like(SysUserEntity::getUserPhone, query.getKeyWord())
        );
        queryWrapper.like(condition(userName), SysUserEntity::getUserName, userName);
        queryWrapper.like(condition(nickName), SysUserEntity::getNickName, nickName);
        queryWrapper.eq(condition(userType), SysUserEntity::getUserType, userType);
        queryWrapper.eq(condition(userStatus), SysUserEntity::getUserStatus, userStatus);
        queryWrapper.like(condition(userPhone), SysUserEntity::getUserPhone, userPhone);
        queryWrapper.eq(condition(query.getDeptId()), SysUserEntity::getDeptId, query.getDeptId());
        return page(page, queryWrapper);
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public SysUserVo findInfoByUserId(Long userId) {
        return baseMapper.findInfoByUserId(userId);
    }

    /**
     * 根据手机号校验用户是否重复
     *
     * @param userPhone 手机号
     * @param neUserId  不包括的用户ID
     * @return true：存在；false：不存在
     */
    @Override
    public boolean checkUserPhoneExists(String userPhone, Long neUserId) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper
                .select(SysUserEntity::getUserStatus)
                .eq(SysUserEntity::getUserPhone, userPhone)
                .ne(Objects.nonNull(neUserId), SysUserEntity::getId, neUserId);
        // @formatter:on
        return SqlHelper.retBool(count(queryWrapper));
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
