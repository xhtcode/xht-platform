package com.xht.modules.admin.system.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.api.system.domain.response.SysPostResponse;
import com.xht.modules.admin.system.dao.SysUserPostDao;
import com.xht.modules.admin.system.dao.mapper.SysUserPostMapper;
import com.xht.modules.admin.system.entity.SysUserPostEntity;
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
        List<SysPostResponse> postByUserId = baseMapper.getPostByUserId(userId);
        return CollectionUtil.emptyIfNull(postByUserId);
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
