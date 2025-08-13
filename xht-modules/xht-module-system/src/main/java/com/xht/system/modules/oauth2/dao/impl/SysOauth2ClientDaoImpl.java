package com.xht.system.modules.oauth2.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.oauth2.dao.SysOauth2ClientDao;
import com.xht.system.modules.oauth2.dao.mapper.SysOauth2ClientMapper;
import com.xht.system.modules.oauth2.domian.entity.SysOauth2ClientEntity;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientFormRequest;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientQueryRequest;
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
     * @param formRequest OAuth2客户端修改信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(SysOauth2ClientFormRequest formRequest) {
        LambdaUpdateWrapper<SysOauth2ClientEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(condition(formRequest.getClientId()), SysOauth2ClientEntity::getClientId, formRequest.getClientId())
                .set(condition(formRequest.getClientName()), SysOauth2ClientEntity::getClientName, formRequest.getClientName())
                .set(condition(formRequest.getClientSecret()), SysOauth2ClientEntity::getClientSecret, formRequest.getClientSecret())
                .set(condition(formRequest.getClientIdIssuedAt()), SysOauth2ClientEntity::getClientIdIssuedAt, formRequest.getClientIdIssuedAt())
                .set(condition(formRequest.getClientSecretExpiresAt()), SysOauth2ClientEntity::getClientSecretExpiresAt, formRequest.getClientSecretExpiresAt())
                .set(condition(formRequest.getAuthorizationGrantTypes()), SysOauth2ClientEntity::getAuthorizationGrantTypes, formRequest.getAuthorizationGrantTypes(), JACKSON_TYPE_HANDLER)
                .set(condition(formRequest.getScopes()), SysOauth2ClientEntity::getScopes, formRequest.getScopes(), JACKSON_TYPE_HANDLER)
                .set(condition(formRequest.getRedirectUris()), SysOauth2ClientEntity::getRedirectUris, formRequest.getRedirectUris(), JACKSON_TYPE_HANDLER)
                .set(condition(formRequest.getAccessTokenValidity()), SysOauth2ClientEntity::getAccessTokenValidity, formRequest.getAccessTokenValidity())
                .set(condition(formRequest.getRefreshTokenValidity()), SysOauth2ClientEntity::getRefreshTokenValidity, formRequest.getRefreshTokenValidity())
                .set(condition(formRequest.getAdditionalInformation()), SysOauth2ClientEntity::getAdditionalInformation, formRequest.getAdditionalInformation(), JACKSON_TYPE_HANDLER)
                .set(condition(formRequest.getAutoApprove()), SysOauth2ClientEntity::getAutoApprove, formRequest.getAutoApprove());
        // @formatter:on
        updateWrapper.eq(SysOauth2ClientEntity::getId, formRequest.getId());
        return update(updateWrapper);
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
     * @param page         分页信息
     * @param queryRequest 查询参数
     * @return 分页结果
     */
    @Override
    public Page<SysOauth2ClientEntity> queryPageRequest(Page<SysOauth2ClientEntity> page, SysOauth2ClientQueryRequest queryRequest) {
        LambdaQueryWrapper<SysOauth2ClientEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        condition(queryRequest.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysOauth2ClientEntity::getClientId, queryRequest.getKeyWord())
                                .or()
                                .like(SysOauth2ClientEntity::getClientName, queryRequest.getKeyWord())
                )
                .like(condition(queryRequest.getClientId()), SysOauth2ClientEntity::getClientId, queryRequest.getClientId())
                .like(condition(queryRequest.getClientName()), SysOauth2ClientEntity::getClientName, queryRequest.getClientName())
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
