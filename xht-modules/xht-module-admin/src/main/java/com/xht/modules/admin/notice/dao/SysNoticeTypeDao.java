package com.xht.modules.admin.notice.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.notice.domain.form.SysNoticeTypeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeTypeQuery;
import com.xht.modules.admin.notice.entity.SysNoticeTypeEntity;

import java.util.List;

/**
 * 描述 ： 系统管理-通知类型 Dao
 *
 * @author xht
 **/
public interface SysNoticeTypeDao extends MapperRepository<SysNoticeTypeEntity> {

    /**
     * 更新通知类型
     *
     * @param form 通知类型
     */
    void updateFormRequest(SysNoticeTypeForm form);

    /**
     * 查询全部通知类型
     *
     * @param query 通知类型查询请求参数
     * @return 通知类型分页信息
     */
    List<SysNoticeTypeEntity> findList(SysNoticeTypeQuery query);

}
