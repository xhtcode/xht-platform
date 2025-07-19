package com.xht.system.modules.log.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.dao.BasicDao;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import com.xht.system.modules.log.domian.request.SysLogQueryRequest;
import com.xht.system.modules.log.mapper.SysLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 系统日志
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysLogDao extends BasicDao<SysLogMapper, SysLogEntity> {

    /**
     * 分页查询系统日志
     *
     * @param page         分页信息
     * @param queryRequest 系统日志管理查询请求参数
     * @return 分页查询系统日志
     */
    public Page<SysLogEntity> queryPageRequest(Page<SysLogEntity> page, SysLogQueryRequest queryRequest) {
        LambdaQueryWrapper<SysLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        StringUtils.hasText(queryRequest.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysLogEntity::getTitle, queryRequest.getKeyWord())
                                .or()
                                .like(SysLogEntity::getServiceName, queryRequest.getKeyWord())
                                .or()
                                .like(SysLogEntity::getDescription, queryRequest.getKeyWord())
                )
                .like(StringUtils.hasText(queryRequest.getTitle()), SysLogEntity::getTitle, queryRequest.getTitle())
                .like(StringUtils.hasText(queryRequest.getServiceName()), SysLogEntity::getServiceName, queryRequest.getServiceName())
                .like(StringUtils.hasText(queryRequest.getDescription()), SysLogEntity::getDescription, queryRequest.getDescription())
                .eq(Objects.nonNull(queryRequest.getStatus()), SysLogEntity::getStatus, queryRequest.getStatus())
        ;
        // @formatter:on
        return page(page, queryWrapper);
    }
}
