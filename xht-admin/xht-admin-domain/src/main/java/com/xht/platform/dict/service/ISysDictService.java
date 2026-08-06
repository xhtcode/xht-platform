package com.xht.platform.dict.service;

import com.xht.framework.common.domain.response.PageResponse;
import  com.xht.platform.dict.domain.form.SysDictForm;
import  com.xht.platform.dict.domain.query.SysDictQuery;
import  com.xht.platform.dict.domain.response.SysDictResponse;

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
     * @param dictId ID列表
     */
    void removeById(Long dictId);

    /**
     * 修改字典类型
     *
     * @param dictId 字典ID
     * @param form 更新请求
     */
    void updateById(Long dictId, SysDictForm form);

    /**
     * 获取字典类型详情
     *
     * @param dictId 字典ID
     * @return 字典详情
     */
    SysDictResponse findById(Long dictId);

    /**
     * 分页查询字典类型
     *
     * @param query 系统字典查询参数
     * @return 分页结果
     */
    PageResponse<SysDictResponse> findPageList(SysDictQuery query);

}
