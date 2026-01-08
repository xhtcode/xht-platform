package com.xht.modules.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.system.dao.SysOauth2ClientDao;
import com.xht.modules.system.dao.mapper.SysOauth2ClientMapper;
import com.xht.modules.system.entity.SysOauth2ClientEntity;
import com.xht.api.system.domain.form.SysOauth2ClientForm;
import com.xht.api.system.domain.query.SysOauth2ClientQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.xht.framework.mybatis.constant.MapperConstant.JACKSON_TYPE_HANDLER;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysOauth2ClientDaoImpl extends MapperRepositoryImpl<SysOauth2ClientMapper, SysOauth2ClientEntity> implements SysOauth2ClientDao {

    /**
     * 更新OAuth2客户端信息
     *
     * @param form OAuth2客户端修改信息
     */
    @Override
    public void updateFormRequest(SysOauth2ClientForm form) {
        LambdaUpdateWrapper<SysOauth2ClientEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(condition(form.getClientId()), SysOauth2ClientEntity::getClientId, form.getClientId())
                .set(condition(form.getClientName()), SysOauth2ClientEntity::getClientName, form.getClientName())
                .set(condition(form.getClientSecret()), SysOauth2ClientEntity::getClientSecret, form.getClientSecret())
                .set(condition(form.getClientIdIssuedAt()), SysOauth2ClientEntity::getClientIdIssuedAt, form.getClientIdIssuedAt())
                .set(condition(form.getClientSecretExpiresAt()), SysOauth2ClientEntity::getClientSecretExpiresAt, form.getClientSecretExpiresAt())
                .set(condition(form.getAuthorizationGrantTypes()), SysOauth2ClientEntity::getAuthorizationGrantTypes, form.getAuthorizationGrantTypes(), JACKSON_TYPE_HANDLER)
                .set(condition(form.getScopes()), SysOauth2ClientEntity::getScopes, form.getScopes(), JACKSON_TYPE_HANDLER)
                .set(condition(form.getRedirectUris()), SysOauth2ClientEntity::getRedirectUris, form.getRedirectUris(), JACKSON_TYPE_HANDLER)
                .set(condition(form.getAccessTokenValidity()), SysOauth2ClientEntity::getAccessTokenValidity, form.getAccessTokenValidity())
                .set(condition(form.getRefreshTokenValidity()), SysOauth2ClientEntity::getRefreshTokenValidity, form.getRefreshTokenValidity())
                .set(condition(form.getAdditionalInformation()), SysOauth2ClientEntity::getAdditionalInformation, form.getAdditionalInformation(), JACKSON_TYPE_HANDLER)
                .set(condition(form.getAutoApprove()), SysOauth2ClientEntity::getAutoApprove, form.getAutoApprove());
        // @formatter:on
        updateWrapper.eq(SysOauth2ClientEntity::getId, form.getId());
        update(updateWrapper);
    }

    /**
     * 判断客户端ID是否存在
     *
     * @param clientId 客户端ID
     * @param id       客户端ID
     * @return 是否存在 true：存在 false：不存在
     */
    @Override
    public Boolean existsByClientId(String clientId, Long id) {
        LambdaQueryWrapper<SysOauth2ClientEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOauth2ClientEntity::getClientId, clientId);
        queryWrapper.ne(Objects.nonNull(id), SysOauth2ClientEntity::getId, id);
        return SqlHelper.retBool(count(queryWrapper));
    }

    /**
     * 分页查询OAuth2客户端
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Page<SysOauth2ClientEntity> findPageList(Page<SysOauth2ClientEntity> page, SysOauth2ClientQuery query) {
        LambdaQueryWrapper<SysOauth2ClientEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        condition(query.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysOauth2ClientEntity::getClientId, query.getKeyWord())
                                .or()
                                .like(SysOauth2ClientEntity::getClientName, query.getKeyWord())
                )
                .like(condition(query.getClientId()), SysOauth2ClientEntity::getClientId, query.getClientId())
                .like(condition(query.getClientName()), SysOauth2ClientEntity::getClientName, query.getClientName())
        ;
        // @formatter:on
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysOauth2ClientEntity, ?> getFieldId() {
        return SysOauth2ClientEntity::getId;
    }
}
