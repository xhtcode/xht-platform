package com.xht.system.modules.dict.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.dict.domain.entity.SysDictEntity;
import com.xht.system.modules.dict.domain.request.SysDictFormRequest;
import com.xht.system.modules.dict.domain.request.SysDictQueryRequest;

/**
 * 系统字典管理
 *
 * @author xht
 **/
public interface SysDictDao extends MapperRepository<SysDictEntity> {

    /**
     * 修改系统字典
     *
     * @param formRequest      系统字典修改参数
     * @param updateItemStatus 是否更新字典项状态
     * @return 修改系统字典
     */
    Boolean updateRequest(SysDictFormRequest formRequest, boolean updateItemStatus);

    /**
     * 根据字典ID和字典项编码检查是否存在相同的字典项编码
     *
     * @param dictId   字典id
     * @param dictCode 字典code
     * @return 存在相同的字典项编码返回true，否则返回false
     */
    Boolean checkDictCode(Long dictId, String dictCode);

    /**
     * 查询系统字典列表
     *
     * @param queryRequest 系统字典查询参数
     * @return 系统字典列表
     */
    Page<SysDictEntity> queryPageRequest(Page<SysDictEntity> page, SysDictQueryRequest queryRequest);
}
