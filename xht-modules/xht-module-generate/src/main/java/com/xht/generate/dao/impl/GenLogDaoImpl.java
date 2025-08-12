package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenLogDao;
import com.xht.generate.dao.mapper.GenLogMapper;
import com.xht.generate.domain.entity.GenLogEntity;
import com.xht.generate.domain.request.GenLogQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 生成日志管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenLogDaoImpl extends MapperRepositoryImpl<GenLogMapper, GenLogEntity> implements GenLogDao {

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param queryRequest 菜单查询请求参数
     * @return 菜单分页信息
     */
    @Override
    public Page<GenLogEntity> queryPageRequest(Page<GenLogEntity> page, GenLogQueryRequest queryRequest) {
        LambdaQueryWrapper<GenLogEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(Objects.nonNull(queryRequest.getGroupId()), GenLogEntity::getGroupId, queryRequest.getGroupId());
        queryWrapper.eq(StringUtils.hasText(queryRequest.getBatchNo()), GenLogEntity::getBatchNo, queryRequest.getBatchNo());
        queryWrapper.ge(Objects.nonNull(queryRequest.getGenerateTimeStart()), GenLogEntity::getGenerateTime, queryRequest.getGenerateTimeStart());
        queryWrapper.le(Objects.nonNull(queryRequest.getGenerateTimeEnd()), GenLogEntity::getGenerateTime, queryRequest.getGenerateTimeEnd());
        return page(page, queryWrapper);
    }

}
