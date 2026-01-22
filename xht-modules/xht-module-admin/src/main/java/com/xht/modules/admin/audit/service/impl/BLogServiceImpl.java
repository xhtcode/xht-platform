package com.xht.modules.admin.audit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.modules.admin.audit.converter.BLogConverter;
import com.xht.modules.admin.audit.dao.BLogDao;
import com.xht.modules.admin.audit.domain.query.BLogQuery;
import com.xht.modules.admin.audit.domain.response.BLogResponse;
import com.xht.modules.admin.audit.entity.BLogEntity;
import com.xht.modules.admin.audit.service.IBLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统日志
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class BLogServiceImpl implements IBLogService {

    private final BLogConverter bLogConverter;

    private final BLogDao bLogDao;

    /**
     * 创建系统日志
     *
     * @param bLogDTO 系统日志表单请求参数
     */
    @Override
    public void create(BLogDTO bLogDTO) {
        BLogEntity entity = bLogConverter.toEntity(bLogDTO);
        bLogDao.saveTransactional(entity);
    }

    /**
     * 获取系统日志详情
     *
     * @param id 系统日志ID
     * @return 系统日志详情
     */
    @Override
    public BLogResponse findById(Long id) {
        BLogEntity bLogEntity = bLogDao.findById(id);
        return bLogConverter.toResponse(bLogEntity);
    }

    /**
     * 分页查询系统日志岗位
     *
     * @param query 系统日志岗位查询请求参数
     * @return 系统日志岗位分页信息
     */
    @Override
    public PageResponse<BLogResponse> findPageList(BLogQuery query) {
        Page<BLogEntity> page = bLogDao.findPageList(PageTool.getPage(query), query);
        return bLogConverter.toResponse(page);
    }
}
