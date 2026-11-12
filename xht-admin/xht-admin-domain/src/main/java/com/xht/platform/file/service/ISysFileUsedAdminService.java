package com.xht.platform.file.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.platform.file.domain.form.SysFileUsedForm;
import com.xht.platform.file.domain.query.SysFileUsedQuery;
import com.xht.platform.file.domain.response.SysFileUsedResponse;

import java.util.List;

/**
 * 描述： 文件用途管理服务接口
 *
 * @author xht
 **/
public interface ISysFileUsedAdminService {

    /**
     * 启用用途列表
     *
     * @return 用途列表
     */
    List<SysFileUsedResponse> listEnabled();

    /**
     * 分页查询用途
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    PageResponse<SysFileUsedResponse> findPageList(SysFileUsedQuery query);

    /**
     * 根据ID查询用途
     *
     * @param id 用途ID
     * @return 用途详情
     */
    SysFileUsedResponse findById(Long id);

    /**
     * 新增用途
     *
     * @param form 表单
     */
    void create(SysFileUsedForm form);

    /**
     * 更新用途
     *
     * @param id   用途ID
     * @param form 表单
     */
    void update(Long id, SysFileUsedForm form);

    /**
     * 停用用途
     *
     * @param id 用途ID
     */
    void disable(Long id);

    /**
     * 删除用途
     *
     * @param id 用途ID
     */
    void remove(Long id);

}
