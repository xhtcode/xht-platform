package com.xht.modules.admin.oauth2.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.modules.admin.oauth2.domain.form.SysOauth2ClientForm;
import com.xht.modules.admin.oauth2.domain.form.SysOauth2ClientPwdForm;
import com.xht.modules.admin.oauth2.domain.query.SysOauth2ClientQuery;
import com.xht.modules.admin.oauth2.domain.response.SysOauth2ClientResponse;

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
     * @param ids OAuth2客户端标识集合
     */
    void removeById(List<Long> ids);

    /**
     * 修改OAuth2客户端
     *
     * @param form OAuth2客户端信息
     */
    void updateById(SysOauth2ClientForm form);

    /**
     * 根据clientId 修改客户端密钥
     *
     * @param pwdForm 客户端信息
     */
    void updateClientSecret(SysOauth2ClientPwdForm pwdForm);

    /**
     * 获取OAuth2客户端详情
     *
     * @param id OAuth2客户端标识
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

}
