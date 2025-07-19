package com.xht.system.modules.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.modules.log.converter.SysLogConverter;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import com.xht.system.modules.log.domian.request.SysLogFormRequest;
import com.xht.system.modules.log.domian.request.SysLogQueryRequest;
import com.xht.system.modules.log.domian.response.SysLogResponse;
import com.xht.system.modules.log.dao.SysLogDao;
import com.xht.system.modules.log.service.ISysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统日志管理
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl implements ISysLogService {

    private final SysLogConverter sysLogConverter;

    private final SysLogDao sysLogDao;

    /**
     * 创建系统日志
     *
     * @param formRequest 系统日志表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(SysLogFormRequest formRequest) {
        SysLogEntity entity = sysLogConverter.toEntity(formRequest);
        return sysLogDao.saveTransactional(entity);
    }

    /**
     * 获取系统日志详情
     *
     * @param id 系统日志ID
     * @return 系统日志详情
     */
    @Override
    public SysLogResponse getById(Long id) {
        SysLogEntity sysLogEntity = sysLogDao.getById(id);
        return sysLogConverter.toResponse(sysLogEntity);
    }

    /**
     * 分页查询系统日志岗位
     *
     * @param queryRequest 系统日志岗位查询请求参数
     * @return 系统日志岗位分页信息
     */
    @Override
    public PageResponse<SysLogResponse> findPage(SysLogQueryRequest queryRequest) {
        if (Objects.isNull(queryRequest)) {
            return PageTool.empty();
        }
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
        Page<SysLogEntity> page = sysLogDao.page(PageTool.getPage(queryRequest), queryWrapper);
        return sysLogConverter.toResponse(page);
    }
}
