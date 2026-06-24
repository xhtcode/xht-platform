package com.xht.auth.consent.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.auth.consent.dao.ISysOauth2AuthorizationConsentDao;
import com.xht.auth.consent.dao.mapper.SysOauth2AuthorizationConsentMapper;
import com.xht.auth.consent.entity.SysOauth2AuthorizationConsentEntity;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class SysOauth2AuthorizationConsentDaoImpl extends MapperRepositoryImpl<SysOauth2AuthorizationConsentMapper, SysOauth2AuthorizationConsentEntity> implements ISysOauth2AuthorizationConsentDao {

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysOauth2AuthorizationConsentEntity, ?> getFieldId() {
        return SysOauth2AuthorizationConsentEntity::getId;
    }

    /**
     * 根据注册客户端id和主体名称删除授权确认信息
     *
     * @param registeredClientId 注册客户端id
     * @param principalName      主体名称
     */
    @Override
    public void removeByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName) {
        LambdaQueryWrapper<SysOauth2AuthorizationConsentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOauth2AuthorizationConsentEntity::getRegisteredClientId, registeredClientId);
        queryWrapper.eq(SysOauth2AuthorizationConsentEntity::getPrincipalName, principalName);
        remove(queryWrapper);
    }

    /**
     * 根据注册客户端id和主体名称查询授权确认信息
     * @param registeredClientId 注册客户端id
     * @param principalName 主体名称
     * @param deviceCode 设备码
     * @return 授权确认信息
     */
    @Override
    public List<SysOauth2AuthorizationConsentEntity> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName, String deviceCode) {
        LambdaQueryWrapper<SysOauth2AuthorizationConsentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOauth2AuthorizationConsentEntity::getRegisteredClientId, registeredClientId);
        queryWrapper.eq(SysOauth2AuthorizationConsentEntity::getPrincipalName, principalName);
        queryWrapper.eq(SysOauth2AuthorizationConsentEntity::getDeviceCode, deviceCode);
        return list(queryWrapper);
    }

    /**
     * 根据主体名称查询授权确认信息
     *
     * @param principalName 主体名称
     * @return 授权确认信息
     */
    @Override
    public List<SysOauth2AuthorizationConsentEntity> findByPrincipalName(String principalName) {
        LambdaQueryWrapper<SysOauth2AuthorizationConsentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOauth2AuthorizationConsentEntity::getPrincipalName, principalName);
        return list(queryWrapper);
    }

}
