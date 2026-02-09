package com.xht.modules.admin.notice.service;

import com.xht.framework.core.domain.LabelValue;
import com.xht.modules.admin.notice.domain.form.SysNoticeTypeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeTypeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeTypeResponse;

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
     * @param id 通知类型ID
     */
    void removeById(Long id);

    /**
     * 根据ID更新通知类型
     *
     * @param form 通知类型更新请求参数
     */
    void updateById(SysNoticeTypeForm form);

    /**
     * 根据ID查询通知类型
     *
     * @param id 通知类型ID
     * @return 通知类型信息
     */
    SysNoticeTypeResponse findById(Long id);

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