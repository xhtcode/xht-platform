package com.xht.system.modules.oauth2.service.impl;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.modules.oauth2.converter.SysOauth2ClientConverter;
import com.xht.system.modules.oauth2.dao.SysOauth2ClientDao;
import com.xht.system.modules.oauth2.domian.entity.SysOauth2ClientEntity;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientFormRequest;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientQueryRequest;
import com.xht.system.modules.oauth2.domian.response.SysOauth2ClientResponse;
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
     * @param formRequest OAuth2客户端信息
     * @return true成功、false失败
     */
    @Override
    public Boolean create(SysOauth2ClientFormRequest formRequest) {
        Boolean exists = sysOauth2ClientDao.existsByClientId(formRequest.getClientId(), null);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "客户端id已存在.");
        SysOauth2ClientEntity entity = sysOauth2ClientConverter.toEntity(formRequest);
        return sysOauth2ClientDao.saveTransactional(entity);
    }

    /**
     * 删除OAuth2客户端
     *
     * @param ids OAuth2客户端ID集合
     * @return true成功、false失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeById(List<Long> ids) {
        return sysOauth2ClientDao.deleteAllById(ids);
    }

    /**
     * 修改OAuth2客户端
     *
     * @param formRequest OAuth2客户端信息
     * @return true成功、false失败
     */
    @Override
    public Boolean updateById(SysOauth2ClientFormRequest formRequest) {
        Boolean deptExists = sysOauth2ClientDao.exists(SysOauth2ClientEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_NOT_EXIST, "客户端不存在");
        Boolean exists = sysOauth2ClientDao.existsByClientId(formRequest.getClientId(), formRequest.getId());
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "客户端id已存在.");
        return sysOauth2ClientDao.updateFormRequest(formRequest);
    }

    /**
     * 获取OAuth2客户端详情
     *
     * @param id OAuth2客户端ID
     * @return OAuth2客户端详情
     */
    @Override
    public SysOauth2ClientResponse getById(Long id) {
        SysOauth2ClientEntity sysOauth2ClientEntity = sysOauth2ClientDao.findById(id);
        return sysOauth2ClientConverter.toResponse(sysOauth2ClientEntity);
    }

    /**
     * 分页查询OAuth2客户端
     *
     * @param queryRequest 查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<SysOauth2ClientResponse> selectPage(SysOauth2ClientQueryRequest queryRequest) {
        if (Objects.isNull(queryRequest)) {
            return PageTool.empty();
        }
        return sysOauth2ClientConverter.toResponse(sysOauth2ClientDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest));
    }

    /**
     * 根据clientId 获取客户端详情
     *
     * @param clientId 客户端id
     * @return 客户端详情
     */
    @Override
    public SysOauth2ClientResponse getClient(String clientId) {
        SysOauth2ClientEntity sysOauth2ClientEntity = sysOauth2ClientDao.findOneOptional(SysOauth2ClientEntity::getClientId, clientId).orElse(null);
        return sysOauth2ClientConverter.toResponse(sysOauth2ClientEntity);
    }
}
