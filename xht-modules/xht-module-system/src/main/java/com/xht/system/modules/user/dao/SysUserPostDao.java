package com.xht.system.modules.user.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.dept.domain.response.SysPostResp;
import com.xht.system.modules.user.domain.entity.SysUserPostEntity;

import java.util.List;

/**
 * 用户岗位关系Dao
 *
 * @author xht
 **/
public interface SysUserPostDao extends MapperRepository<SysUserPostEntity> {

    /**
     * 根据用户ID获取部门信息
     *
     * @param userId 用户ID
     * @return 部门信息
     */
    List<SysPostResp> getPostByUserId(Long userId);

}
