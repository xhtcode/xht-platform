package com.xht.modules.admin.oauth2.service.impl;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.modules.admin.oauth2.converter.SysOauth2ClientConverter;
import com.xht.modules.admin.oauth2.dao.SysOauth2ClientDao;
import com.xht.modules.admin.oauth2.domain.form.SysOauth2ClientForm;
import com.xht.modules.admin.oauth2.domain.form.SysOauth2ClientPwdForm;
import com.xht.modules.admin.oauth2.domain.query.SysOauth2ClientQuery;
import com.xht.modules.admin.oauth2.domain.response.SysOauth2ClientResponse;
import com.xht.modules.admin.oauth2.entity.SysOauth2ClientEntity;
import com.xht.modules.admin.oauth2.service.ISysOauth2ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOauth2ClientServiceImpl implements ISysOauth2ClientService {

    private final SysOauth2ClientDao sysOauth2ClientDao;

    private final SysOauth2ClientConverter sysOauth2ClientConverter;

    private final PasswordEncoder passwordEncoder;

    /**
     * 创建OAuth2客户端
     *
     * @param form OAuth2客户端信息
     */
    @Override
    public void create(SysOauth2ClientForm form) {
        validation(form);
        Boolean exists = sysOauth2ClientDao.existsByClientId(form.getClientId(), null);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "客户端标识已存在.");
        SysOauth2ClientEntity entity = sysOauth2ClientConverter.toEntity(form);
        entity.setClientIdIssuedAt(LocalDateTime.now());
        entity.setClientSecret(passwordEncoder.encode(form.getClientSecret()));
        sysOauth2ClientDao.saveTransactional(entity);
    }

    /**
     * 删除OAuth2客户端
     *
     * @param ids OAuth2客户端标识集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(List<Long> ids) {
        sysOauth2ClientDao.removeAllById(ids);
    }

    /**
     * 修改OAuth2客户端
     *
     * @param form OAuth2客户端信息
     */
    @Override
    public void updateById(SysOauth2ClientForm form) {
        validation(form);
        Boolean deptExists = sysOauth2ClientDao.exists(SysOauth2ClientEntity::getId, form.getId());
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_NOT_EXIST, "客户端不存在");
        Boolean exists = sysOauth2ClientDao.existsByClientId(form.getClientId(), form.getId());
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "客户端标识已存在.");
        sysOauth2ClientDao.updateFormRequest(form);
    }

    /**
     * 根据clientId 修改客户端密钥
     *
     * @param pwdForm 客户端信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClientSecret(SysOauth2ClientPwdForm pwdForm) {
        ThrowUtils.notNull(pwdForm.getClientId(), "客户端标识不能为空");
        ThrowUtils.notNull(pwdForm.getClientSecret(), "客户端密钥不能为空");
        SysOauth2ClientEntity entity = sysOauth2ClientDao.findOneOptional(SysOauth2ClientEntity::getClientId, pwdForm.getClientId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST));
        String encode = passwordEncoder.encode(pwdForm.getClientSecret());
        ThrowUtils.throwIf(Objects.equals(encode, entity.getClientSecret()), "客户端密钥和原密钥相似");
        sysOauth2ClientDao.updateClientSecret(entity.getId(), encode);
    }

    private void validation(SysOauth2ClientForm form) {
        Set<String> authorizationGrantTypes = form.getAuthorizationGrantTypes();
        if (authorizationGrantTypes.contains(AuthorizationGrantType.AUTHORIZATION_CODE.getValue())) {
            ThrowUtils.notEmpty(form.getRedirectUris(), "回调地址不能为空");
            ThrowUtils.notNull(form.getAutoApprove(), "自动放行状态不能为空");
        }
    }

    /**
     * 获取OAuth2客户端详情
     *
     * @param id OAuth2客户端标识
     * @return OAuth2客户端详情
     */
    @Override
    public SysOauth2ClientResponse findById(Long id) {
        SysOauth2ClientEntity sysOauth2ClientEntity = sysOauth2ClientDao.findById(id);
        return sysOauth2ClientConverter.toResponse(sysOauth2ClientEntity);
    }

    /**
     * 分页查询OAuth2客户端
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<SysOauth2ClientResponse> findPageList(SysOauth2ClientQuery query) {
        return sysOauth2ClientConverter.toResponse(sysOauth2ClientDao.findPageList(PageTool.getPage(query), query));
    }

}
