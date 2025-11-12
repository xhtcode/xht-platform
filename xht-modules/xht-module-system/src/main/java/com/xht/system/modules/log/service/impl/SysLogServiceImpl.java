package com.xht.system.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.modules.log.converter.SysLogConverter;
import com.xht.system.modules.log.dao.SysLogDao;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import com.xht.system.modules.log.domian.request.SysLogQuery;
import com.xht.system.modules.log.domian.response.SysLogResponse;
import com.xht.system.modules.log.service.ISysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统日志
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
     * @param logDTO 系统日志表单请求参数
     */
    @Override
    public void create(LogDTO logDTO) {
        SysLogEntity entity = sysLogConverter.toEntity(logDTO);
        sysLogDao.saveTransactional(entity);
    }

    /**
     * 获取系统日志详情
     *
     * @param id 系统日志ID
     * @return 系统日志详情
     */
    @Override
    public SysLogResponse findById(Long id) {
        SysLogEntity sysLogEntity = sysLogDao.findById(id);
        return sysLogConverter.toResponse(sysLogEntity);
    }

    /**
     * 分页查询系统日志岗位
     *
     * @param query 系统日志岗位查询请求参数
     * @return 系统日志岗位分页信息
     */
    @Override
    public PageResponse<SysLogResponse>findPageList(SysLogQuery query) {
        if (Objects.isNull(query)) {
            return PageTool.empty();
        }
        // @formatter:on
        Page<SysLogEntity> page = sysLogDao.findPageList(PageTool.getPage(query), query);
        return sysLogConverter.toResponse(page);
    }
}
