package com.xht.system.modules.user.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.dept.domain.response.SysPostResponse;
import com.xht.system.modules.user.dao.SysUserPostDao;
import com.xht.system.modules.user.dao.mapper.SysUserPostMapper;
import com.xht.system.modules.user.domain.entity.SysUserPostEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户岗位关系Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysUserPostDaoImpl extends MapperRepositoryImpl<SysUserPostMapper, SysUserPostEntity> implements SysUserPostDao {

    /**
     * 根据用户ID获取部门信息
     *
     * @param userId 用户ID
     * @return 部门信息
     */
    @Override
    public List<SysPostResponse> getPostByUserId(Long userId) {
        return baseMapper.getPostByUserId(userId);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysUserPostEntity, ?> getFieldId() {
        return SysUserPostEntity::getUserId;
    }

}
