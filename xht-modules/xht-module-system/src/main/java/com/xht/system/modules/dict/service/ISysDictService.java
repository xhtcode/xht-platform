package com.xht.system.modules.dict.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.system.modules.dict.domain.request.SysDictForm;
import com.xht.system.modules.dict.domain.request.SysDictQuery;
import com.xht.system.modules.dict.domain.response.SysDictResp;

import java.util.List;

/**
 * 字典管理
 *
 * @author xht
 */
public interface ISysDictService {

    /**
     * 创建字典类型
     *
     * @param form 创建请求
     */
    void create(SysDictForm form);

    /**
     * 删除字典类型
     *
     * @param ids ID列表
     */
    void removeById(List<Long> ids);

    /**
     * 修改字典类型
     *
     * @param form 更新请求
     */
    void updateById(SysDictForm form);

    /**
     * 获取字典类型详情
     *
     * @param id 字典ID
     * @return 字典详情
     */
    SysDictResp findById(Long id);

    /**
     * 分页查询字典类型
     *
     * @param query 系统字典查询参数
     * @return 分页结果
     */
    PageResponse<SysDictResp> pageList(SysDictQuery query);


}

