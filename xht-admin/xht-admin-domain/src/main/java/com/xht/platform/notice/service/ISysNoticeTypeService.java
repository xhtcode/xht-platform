package com.xht.platform.notice.service;

import com.xht.framework.common.domain.LabelValue;
import  com.xht.platform.notice.domain.form.SysNoticeTypeForm;
import  com.xht.platform.notice.domain.query.SysNoticeTypeQuery;
import  com.xht.platform.notice.domain.response.SysNoticeTypeResponse;

import java.util.List;

/**
 * 系统管理-通知类型 的数据库操作Service
 *
 * @author admin
 */
public interface ISysNoticeTypeService {

    /**
     * 创建通知类型
     *
     * @param form 通知类型表单请求参数
     */
    void create(SysNoticeTypeForm form);

    /**
     * 根据ID删除通知类型
     *
     * @param noticeTypeId 通知类型ID
     */
    void removeById(Long noticeTypeId);

    /**
     * 根据ID更新通知类型
     *
     * @param noticeTypeId 通知类型ID
     * @param form 通知类型更新请求参数
     */
    void updateById(Long noticeTypeId, SysNoticeTypeForm form);

    /**
     * 根据ID查询通知类型
     *
     * @param noticeTypeId 通知类型ID
     * @return 通知类型信息
     */
    SysNoticeTypeResponse findById(Long noticeTypeId);

    /**
     * 查询所有通知类型
     * @param query 通知类型查询请求参数
     * @return 通知类型列表
     */
    List<SysNoticeTypeResponse> list(SysNoticeTypeQuery query);

    /**
     * 获取所有通知类型
     * @return 获取所有通知类型
     */
    List<LabelValue<Long, String>> findEnableList();

}