package com.xht.system.modules.dict.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.system.modules.dict.domain.request.SysDictFormRequest;
import com.xht.system.modules.dict.domain.request.SysDictQueryRequest;
import com.xht.system.modules.dict.domain.response.SysDictResponse;

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
     * @param formRequest 创建请求
     * @return 是否成功
     */
    Boolean create(SysDictFormRequest formRequest);

    /**
     * 删除字典类型
     *
     * @param ids ID列表
     * @return 是否成功
     */
    Boolean removeById(List<Long> ids);

    /**
     * 修改字典类型
     *
     * @param formRequest 更新请求
     * @return 是否成功
     */
    Boolean updateById(SysDictFormRequest formRequest);

    /**
     * 获取字典类型详情
     *
     * @param id 字典ID
     * @return 字典详情
     */
    SysDictResponse findById(Long id);

    /**
     * 分页查询字典类型
     *
     * @param queryRequest 系统字典查询参数
     * @return 分页结果
     */
    PageResponse<SysDictResponse> pageList(SysDictQueryRequest queryRequest);


}

