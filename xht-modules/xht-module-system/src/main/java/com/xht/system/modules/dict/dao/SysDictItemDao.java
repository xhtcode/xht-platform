package com.xht.system.modules.dict.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.dict.domain.entity.SysDictItemEntity;
import com.xht.system.modules.dict.domain.request.SysDictItemFormRequest;
import com.xht.system.modules.dict.domain.request.SysDictItemQueryRequest;

import java.util.List;

/**
 * 系统字典管理
 *
 * @author xht
 **/
public interface SysDictItemDao extends MapperRepository<SysDictItemEntity> {

    /**
     * 根据更新请求更新指定ID的字典项实体
     *
     * @param formRequest 更新请求
     * @return 更新结果
     */
    boolean updateFormRequest(SysDictItemFormRequest formRequest);

    /**
     * 检查字典项值是否存在
     *
     * @param dictId    字典ID
     * @param itemValue 字典项值
     * @return 存在相同的字典项编码返回true，否则返回false
     */
    Boolean checkDictValue(Long id, Long dictId, String itemValue);

    /**
     * 分页查询字典项列表
     *
     * @param page         分页信息
     * @param queryRequest 字典项查询请求参数
     * @return 分页字典项列表
     */
    Page<SysDictItemEntity> queryPageRequest(Page<SysDictItemEntity> page, SysDictItemQueryRequest queryRequest);

    /**
     * 根据字典id查询字典项列表
     *
     * @param dictId 字典id
     * @return 字典项列表
     */
    List<SysDictItemEntity> selectByDictId(Long dictId);
}
