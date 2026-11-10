package com.xht.platform.file.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
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
     * 查询全部用途
     *
     * @return 用途列表
     */
    List<SysFileUsedEntity> findAll();

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
