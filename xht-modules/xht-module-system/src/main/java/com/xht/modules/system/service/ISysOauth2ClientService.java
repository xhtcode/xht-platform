package com.xht.modules.system.service;

import com.xht.api.system.domain.form.SysOauth2ClientForm;
import com.xht.api.system.domain.query.SysOauth2ClientQuery;
import com.xht.api.system.domain.response.SysOauth2ClientResponse;
import com.xht.framework.core.domain.response.PageResponse;

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
     * @param form OAuth2客户端信息
     */
    void create(SysOauth2ClientForm form);

    /**
     * 删除OAuth2客户端
     *
     * @param ids OAuth2客户端ID集合
     */
    void removeById(List<Long> ids);

    /**
     * 修改OAuth2客户端
     *
     * @param form OAuth2客户端信息
     */
    void updateById(SysOauth2ClientForm form);

    /**
     * 获取OAuth2客户端详情
     *
     * @param id OAuth2客户端ID
     * @return OAuth2客户端详情
     */
    SysOauth2ClientResponse findById(Long id);

    /**
     * 分页查询OAuth2客户端
     *
     * @param query 查询请求参数
     * @return 分页结果
     */
    PageResponse<SysOauth2ClientResponse> findPageList(SysOauth2ClientQuery query);

    /**
     * 根据clientId 获取客户端详情
     *
     * @param clientId 客户端id
     * @return 客户端详情
     */
    SysOauth2ClientResponse getClient(String clientId);
}
