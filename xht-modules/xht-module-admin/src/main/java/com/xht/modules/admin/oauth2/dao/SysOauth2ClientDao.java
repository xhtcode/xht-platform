package com.xht.modules.admin.oauth2.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.oauth2.domain.form.SysOauth2ClientForm;
import com.xht.modules.admin.oauth2.domain.query.SysOauth2ClientQuery;
import com.xht.modules.admin.oauth2.entity.SysOauth2ClientEntity;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
public interface SysOauth2ClientDao extends MapperRepository<SysOauth2ClientEntity> {

    /**
     * 更新OAuth2客户端信息
     *
     * @param form OAuth2客户端修改信息
     */
    void updateFormRequest(SysOauth2ClientForm form);

    /**
     * 根据clientId 修改客户端密钥
     *
     * @param id           客户端id
     * @param clientSecret 密钥
     */
    void updateClientSecret(Long id, String clientSecret);

    /**
     * 判断客户端标识是否存在
     *
     * @param clientId 客户端标识
     * @param id       客户端标识
     * @return 是否存在 true：存在 false：不存在
     */
    Boolean existsByClientId(String clientId, Long id);

    /**
     * 分页查询OAuth2客户端
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页结果
     */
    Page<SysOauth2ClientEntity> findPageList(Page<SysOauth2ClientEntity> page, SysOauth2ClientQuery query);

}
