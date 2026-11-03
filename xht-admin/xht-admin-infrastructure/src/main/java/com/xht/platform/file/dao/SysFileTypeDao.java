package com.xht.platform.file.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.file.domain.form.SysFileTypeForm;
import com.xht.platform.file.domain.query.SysFileTypeQuery;
import com.xht.platform.file.entity.SysFileTypeEntity;

/**
 * 描述： 文件预览类型配置数据访问层
 *
 * @author xht
 **/
public interface SysFileTypeDao extends MapperRepository<SysFileTypeEntity> {

    /**
     * 分页查询文件预览类型配置
     *
     * @param page  分页对象
     * @param query 查询参数
     * @return 分页结果
     */
    Page<SysFileTypeEntity> findPageList(Page<SysFileTypeEntity> page, SysFileTypeQuery query);

    /**
     * 判断指定文件后缀是否已配置预览类型
     *
     * @param fileSuffix 文件后缀
     * @param excludeId  需要排除的配置ID（更新场景），可为 null
     * @return true：已存在；false：不存在
     */
    boolean existsBySuffix(String fileSuffix, Long excludeId);

    /**
     * 根据文件后缀查询配置
     *
     * @param fileSuffix 文件后缀
     * @return 配置实体，未配置返回 null
     */
    SysFileTypeEntity findBySuffix(String fileSuffix);

    /**
     * 根据ID更新配置（仅更新表单中的字段）
     *
     * @param fileTypeId 配置ID
     * @param form       配置表单
     */
    void updateFormRequest(Long fileTypeId, SysFileTypeForm form);

}
