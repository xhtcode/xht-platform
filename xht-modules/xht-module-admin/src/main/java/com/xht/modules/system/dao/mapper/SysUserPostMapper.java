package com.xht.modules.system.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.api.system.domain.response.SysPostResponse;
import com.xht.modules.system.entity.SysUserPostEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户岗位关系
 *
 * @author xht
 */
@Mapper
public interface SysUserPostMapper extends BaseMapperX<SysUserPostEntity> {

    /**
     * 根据用户ID查询部门岗位信息
     *
     * @param userId 用户ID
     * @return 部门岗位信息
     */
    List<SysPostResponse> getPostByUserId(Long userId);
}




