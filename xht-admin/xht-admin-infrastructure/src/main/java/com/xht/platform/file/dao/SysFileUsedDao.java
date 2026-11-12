package com.xht.platform.file.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.file.domain.query.SysFileUsedQuery;
import com.xht.platform.file.entity.SysFileUsedEntity;

import java.util.List;
import java.util.Optional;

/**
 * 描述： 文件用途配置数据访问层
 *
 * @author xht
 **/
public interface SysFileUsedDao extends MapperRepository<SysFileUsedEntity> {

    /**
     * 根据用途编码查询
     *
     * @param usedCode 用途编码
     * @return 用途配置
     */
    Optional<SysFileUsedEntity> findByCode(String usedCode);

    /**
     * 分页查询用途
     * <p>
     * 查询条件均为动态条件：用途编码、用途名称模糊匹配，其余字段非空时才参与等值过滤
     *
     * @param page  分页对象（包含分页参数与排序信息）
     * @param query 查询参数
     * @return 分页结果
     */
    Page<SysFileUsedEntity> findPageList(Page<SysFileUsedEntity> page, SysFileUsedQuery query);

    /**
     * 查询全部启用用途
     *
     * @return 启用用途列表
     */
    List<SysFileUsedEntity> findAllEnabled();

    /**
     * 判断用途编码是否存在
     *
     * @param usedCode  用途编码
     * @param excludeId 排除的ID，可为 null
     * @return true：存在
     */
    boolean existsByCode(String usedCode, Long excludeId);

}
