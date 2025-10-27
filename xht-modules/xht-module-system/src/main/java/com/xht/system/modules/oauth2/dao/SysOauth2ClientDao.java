package com.xht.system.modules.oauth2.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.oauth2.domian.entity.SysOauth2ClientEntity;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientForm;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientQuery;

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
     * 判断客户端ID是否存在
     *
     * @param clientId 客户端ID
     * @param id       客户端ID
     * @return 是否存在 true：存在 false：不存在
     */
    Boolean existsByClientId(String clientId, Long id);

    /**
     * 分页查询OAuth2客户端
     *
     * @param page         分页信息
     * @param query 查询参数
     * @return 分页结果
     */
    Page<SysOauth2ClientEntity> findPageList(Page<SysOauth2ClientEntity> page, SysOauth2ClientQuery query);
}
