package com.xht.modules.admin.dict.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.modules.admin.dict.domain.form.SysDictItemForm;
import com.xht.modules.admin.dict.domain.query.SysDictItemQuery;
import com.xht.modules.admin.dict.domain.response.SysDictItemResponse;
import com.xht.platform.common.dict.domain.vo.DictVo;

import java.util.List;

/**
 * 字典项Service接口
 *
 * @author xht
 */
@SuppressWarnings("all")
public interface ISysDictItemService {
    /**
     * 根据创建请求创建系统字典项
     *
     * @param form 系统字典项表单请求参数
     * @return 创建的系统字典项实体
     */
    Boolean create(SysDictItemForm form);

    /**
     * 根据ID删除系统字典项
     *
     * @param ids 系统字典项ID
     * @return 删除是否成功
     */
    boolean removeById(List<Long> ids);

    /**
     * 根据ID更新系统字典项
     *
     * @param form 系统字典项更新请求参数
     * @return 更新是否成功
     */
    boolean updateById(SysDictItemForm form);

    /**
     * 根据ID获取系统字典项
     *
     * @param id 系统字典项ID
     * @return 系统字典项响应信息
     */
    SysDictItemResponse findById(Long id);

    /**
     * 分页查询系统字典项
     *
     * @param query 系统字典项查询请求参数
     * @return 分页响应结果，包含系统字典项响应信息
     */
    PageResponse<SysDictItemResponse> findPageList(SysDictItemQuery query);

    /**
     * 根据字典编码获取系统字典项列表
     *
     * @param dictCode 字典编码
     * @return 系统字典项VO列表
     */
    List<DictVo> getByDictCode(String dictCode);

}
