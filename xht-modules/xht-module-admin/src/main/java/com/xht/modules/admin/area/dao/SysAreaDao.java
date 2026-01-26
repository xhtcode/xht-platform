package com.xht.modules.admin.area.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.area.entity.SysAreaEntity;

import java.util.List;

/**
 * 系统管理 - 行政区划
 *
 * @author xht
 **/
public interface SysAreaDao extends MapperRepository<SysAreaEntity> {


    /**
     * 根据上级区划编码查询子区划
     *
     * @param parentCode 上级区划编码
     * @return 子区划列表
     */
    List<SysAreaEntity> listByParentCode(String parentCode);

}
