package com.xht.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.modules.system.converter.SysLogConverter;
import com.xht.modules.system.dao.SysLogDao;
import com.xht.modules.system.domain.entity.SysLogEntity;
import com.xht.modules.system.domain.request.SysLogQuery;
import com.xht.modules.system.domain.response.SysLogResponse;
import com.xht.modules.system.service.ISysLogService;
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
     * @param bLogDTO 系统日志表单请求参数
     */
    @Override
    public void create(BLogDTO bLogDTO) {
        SysLogEntity entity = sysLogConverter.toEntity(bLogDTO);
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
    public PageResponse<SysLogResponse> findPageList(SysLogQuery query) {
        if (Objects.isNull(query)) {
            return PageTool.empty();
        }
        // @formatter:on
        Page<SysLogEntity> page = sysLogDao.findPageList(PageTool.getPage(query), query);
        return sysLogConverter.toResponse(page);
    }
}
