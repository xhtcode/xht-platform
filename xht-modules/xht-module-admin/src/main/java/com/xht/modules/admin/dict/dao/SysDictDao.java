package com.xht.modules.admin.dict.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.dict.entity.SysDictEntity;
import com.xht.modules.admin.dict.domain.form.SysDictForm;
import com.xht.modules.admin.dict.domain.query.SysDictQuery;

import java.util.List;

/**
 * 系统字典管理
 *
 * @author xht
 **/
public interface SysDictDao extends MapperRepository<SysDictEntity> {

    /**
     * 修改系统字典
     *
     * @param form             系统字典修改参数
     * @param updateItemStatus 是否更新字典项状态
     */
    void updateRequest(SysDictForm form, boolean updateItemStatus);

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
     * @param page 分页参数
     * @param query 系统字典查询参数
     * @return 系统字典列表
     */
    Page<SysDictEntity> findPageList(Page<SysDictEntity> page, SysDictQuery query);

    /**
     * 查询所有字典项
     *
     * @return 字典项列表
     */
    List<SysDictEntity> findAllByStatus();
}
