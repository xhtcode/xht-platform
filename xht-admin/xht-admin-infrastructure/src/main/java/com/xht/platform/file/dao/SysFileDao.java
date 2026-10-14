package com.xht.platform.file.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;

/**
 * 描述： 系统文件数据访问层
 *
 * @author xht
 **/
public interface SysFileDao extends MapperRepository<SysFileEntity> {

    /**
     * 分页查询文件
     * <p>
     * 查询条件均为动态条件：关键字模糊匹配文件原始名称与文件存储名称，
     * 其余字段非空时才参与等值/模糊过滤
     *
     * @param page  分页对象（包含分页参数与排序信息）
     * @param query 查询参数
     * @return 分页结果
     */
    Page<SysFileEntity> findPageList(Page<SysFileEntity> page, SysFileQuery query);

    /**
     * 根据文件ID更新文件状态
     *
     * @param fileId         文件ID
     * @param fileStatusEnum 文件状态枚举
     */
    void updateFileStatusById(Long fileId, FileStatusEnum fileStatusEnum);
}
