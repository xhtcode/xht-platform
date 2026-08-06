package com.xht.platform.notice.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import  com.xht.platform.notice.domain.form.SysNoticeTypeForm;
import  com.xht.platform.notice.domain.query.SysNoticeTypeQuery;
import  com.xht.platform.notice.entity.SysNoticeTypeEntity;

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
     * @param noticeTypeId 通知类型ID
     * @param form         通知类型
     */
    void updateFormRequest(Long noticeTypeId, SysNoticeTypeForm form);

    /**
     * 查询全部通知类型
     *
     * @param query 通知类型查询请求参数
     * @return 通知类型分页信息
     */
    List<SysNoticeTypeEntity> findList(SysNoticeTypeQuery query);

    /**
     * 获取所有通知类型
     *
     * @return 获取所有通知类型
     */
    List<SysNoticeTypeEntity> findEnableList();

    /**
     * 根据通知ID获取通知类型名称
     *
     * @param typeId 通知类型ID
     * @return 通知类型
     */
    String findTypeName(Long typeId);

}
