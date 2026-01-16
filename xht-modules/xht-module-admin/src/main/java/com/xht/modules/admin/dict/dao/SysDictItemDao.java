package com.xht.modules.admin.dict.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.dict.enums.DictStatusEnums;
import com.xht.modules.admin.dict.entity.SysDictItemEntity;
import com.xht.modules.admin.dict.domain.form.SysDictItemForm;
import com.xht.modules.admin.dict.domain.query.SysDictItemQuery;

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
     * @param form 更新请求
     * @param dictCode 字典项编码
     * @return 更新结果
     */
    boolean updateFormRequest(SysDictItemForm form, String dictCode);

    /**
     * 检查字典项值是否存在
     *
     * @param id        字典项ID
     * @param dictId    字典ID
     * @param itemValue 字典项值
     * @return 存在相同的字典项编码返回true，否则返回false
     */
    Boolean checkDictValue(Long id, Long dictId, String itemValue);

    /**
     * 分页查询字典项列表
     *
     * @param page  分页信息
     * @param query 字典项查询请求参数
     * @return 分页字典项列表
     */
    Page<SysDictItemEntity> findPageList(Page<SysDictItemEntity> page, SysDictItemQuery query);

    /**
     * 根据字典编码查询字典项列表
     *
     * @param dictCode 字典编码
     * @param dictStatus  字典状态
     * @return 字典项列表
     */
    List<SysDictItemEntity> findByDictCode(String dictCode, DictStatusEnums dictStatus);
}
