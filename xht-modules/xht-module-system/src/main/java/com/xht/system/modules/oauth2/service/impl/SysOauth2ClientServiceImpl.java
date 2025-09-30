package com.xht.system.modules.oauth2.service.impl;

import com.xht.boot.oauth2.domain.dto.OAuth2RegisteredClientDTO;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.modules.oauth2.converter.SysOauth2ClientConverter;
import com.xht.system.modules.oauth2.dao.SysOauth2ClientDao;
import com.xht.system.modules.oauth2.domian.entity.SysOauth2ClientEntity;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientForm;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientQuery;
import com.xht.system.modules.oauth2.domian.response.SysOauth2ClientResp;
import com.xht.system.modules.oauth2.service.ISysOauth2ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

    /**
     * 创建OAuth2客户端
     *
     * @param form OAuth2客户端信息
     */
    @Override
    public void create(SysOauth2ClientForm form) {
        Boolean exists = sysOauth2ClientDao.existsByClientId(form.getClientId(), null);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "客户端id已存在.");
        SysOauth2ClientEntity entity = sysOauth2ClientConverter.toEntity(form);
        sysOauth2ClientDao.saveTransactional(entity);
    }

    /**
     * 删除OAuth2客户端
     *
     * @param ids OAuth2客户端ID集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(List<Long> ids) {
        sysOauth2ClientDao.deleteAllById(ids);
    }

    /**
     * 修改OAuth2客户端
     *
     * @param form OAuth2客户端信息
     */
    @Override
    public void updateById(SysOauth2ClientForm form) {
        Boolean deptExists = sysOauth2ClientDao.exists(SysOauth2ClientEntity::getId, form.getId());
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_NOT_EXIST, "客户端不存在");
        Boolean exists = sysOauth2ClientDao.existsByClientId(form.getClientId(), form.getId());
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "客户端id已存在.");
        sysOauth2ClientDao.updateFormRequest(form);
    }

    /**
     * 获取OAuth2客户端详情
     *
     * @param id OAuth2客户端ID
     * @return OAuth2客户端详情
     */
    @Override
    public SysOauth2ClientResp findById(Long id) {
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
    public PageResponse<SysOauth2ClientResp> pageList(SysOauth2ClientQuery query) {
        if (Objects.isNull(query)) {
            return PageTool.empty();
        }
        return sysOauth2ClientConverter.toResponse(sysOauth2ClientDao.queryPageRequest(PageTool.getPage(query), query));
    }

    /**
     * 根据clientId 获取客户端详情
     *
     * @param clientId 客户端id
     * @return 客户端详情
     */
    @Override
    public OAuth2RegisteredClientDTO getClient(String clientId) {
        SysOauth2ClientEntity sysOauth2ClientEntity = sysOauth2ClientDao.findOneOptional(SysOauth2ClientEntity::getClientId, clientId).orElse(null);
        return sysOauth2ClientConverter.toDto(sysOauth2ClientEntity);
    }
}
