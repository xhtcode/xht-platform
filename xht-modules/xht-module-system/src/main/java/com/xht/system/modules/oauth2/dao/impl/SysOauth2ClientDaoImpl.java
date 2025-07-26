package com.xht.system.modules.oauth2.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.oauth2.dao.SysOauth2ClientDao;
import com.xht.system.modules.oauth2.domian.entity.SysOauth2ClientEntity;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientFormRequest;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientQueryRequest;
import com.xht.system.modules.oauth2.dao.mapper.SysOauth2ClientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.xht.framework.mybatis.constant.MapperConstant.JACKSON_TYPE_HANDLER;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysOauth2ClientDaoImpl extends MapperRepositoryImpl<SysOauth2ClientMapper, SysOauth2ClientEntity> implements SysOauth2ClientDao {

    /**
     * 更新OAuth2客户端信息
     *
     * @param formRequest OAuth2客户端修改信息
     * @return 是否成功
     */
    public Boolean updateFormRequest(SysOauth2ClientFormRequest formRequest) {
        LambdaUpdateWrapper<SysOauth2ClientEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(SysOauth2ClientEntity::getClientId, formRequest.getClientId())
                .set(SysOauth2ClientEntity::getClientName, formRequest.getClientName())
                .set(SysOauth2ClientEntity::getClientSecret, formRequest.getClientSecret())
                .set(SysOauth2ClientEntity::getClientIdIssuedAt, formRequest.getClientIdIssuedAt())
                .set(SysOauth2ClientEntity::getClientSecretExpiresAt, formRequest.getClientSecretExpiresAt())
                .set(SysOauth2ClientEntity::getAuthorizationGrantTypes, formRequest.getAuthorizationGrantTypes(), JACKSON_TYPE_HANDLER)
                .set(SysOauth2ClientEntity::getScopes, formRequest.getScopes(), JACKSON_TYPE_HANDLER)
                .set(SysOauth2ClientEntity::getRedirectUris, formRequest.getRedirectUris(), JACKSON_TYPE_HANDLER)
                .set(SysOauth2ClientEntity::getAccessTokenValidity, formRequest.getAccessTokenValidity())
                .set(SysOauth2ClientEntity::getRefreshTokenValidity, formRequest.getRefreshTokenValidity())
                .set(SysOauth2ClientEntity::getAdditionalInformation, formRequest.getAdditionalInformation(), JACKSON_TYPE_HANDLER)
                .set(SysOauth2ClientEntity::getAutoApprove, formRequest.getAutoApprove());
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
    public Page<SysOauth2ClientEntity> queryPageRequest(Page<SysOauth2ClientEntity> page, SysOauth2ClientQueryRequest queryRequest) {
        LambdaQueryWrapper<SysOauth2ClientEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        StringUtils.hasText(queryRequest.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysOauth2ClientEntity::getClientId, queryRequest.getKeyWord())
                                .or()
                                .like(SysOauth2ClientEntity::getClientName, queryRequest.getKeyWord())
                )
                .like(StringUtils.hasText(queryRequest.getClientId()), SysOauth2ClientEntity::getClientId, queryRequest.getClientId())
                .like(StringUtils.hasText(queryRequest.getClientName()), SysOauth2ClientEntity::getClientName, queryRequest.getClientName())
        ;
        // @formatter:on
        return page(page, queryWrapper);
    }
}
