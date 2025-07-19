package com.xht.system.modules.oauth2.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientFormRequest;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientQueryRequest;
import com.xht.system.modules.oauth2.domian.response.SysOauth2ClientResponse;

import java.util.List;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
public interface ISysOauth2ClientService {

    /**
     * 创建OAuth2客户端
     *
     * @param formRequest OAuth2客户端信息
     * @return true成功、false失败
     */
    Boolean create(SysOauth2ClientFormRequest formRequest);

    /**
     * 删除OAuth2客户端
     *
     * @param ids OAuth2客户端ID集合
     * @return true成功、false失败
     */
    Boolean deleteById(List<Long> ids);

    /**
     * 修改OAuth2客户端
     *
     * @param formRequest OAuth2客户端信息
     * @return true成功、false失败
     */
    Boolean updateById(SysOauth2ClientFormRequest formRequest);

    /**
     * 获取OAuth2客户端详情
     *
     * @param id OAuth2客户端ID
     * @return OAuth2客户端详情
     */
    SysOauth2ClientResponse getById(Long id);

    /**
     * 分页查询OAuth2客户端
     *
     * @param queryRequest 查询条件
     * @return 分页结果
     */
    PageResponse<SysOauth2ClientResponse> findPage(SysOauth2ClientQueryRequest queryRequest);

    /**
     *  根据clientId 获取客户端详情
     * @param clientId 客户端id
     * @return 客户端详情
     */
    SysOauth2ClientResponse getClient(String clientId);
}
