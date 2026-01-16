package com.xht.modules.admin.audit.service;

import com.xht.modules.admin.audit.domain.response.BLogResponse;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.modules.admin.audit.domain.query.BLogQuery;

/**
 * 系统日志
 *
 * @author xht
 **/
public interface IBLogService {

    /**
     * 创建系统日志
     *
     * @param bLogDTO 系统日志表单请求参数
     */
    void create(BLogDTO bLogDTO);

    /**
     * 获取系统日志详情
     *
     * @param id 系统日志ID
     * @return 系统日志详情
     */
    BLogResponse findById(Long id);

    /**
     * 分页查询系统日志岗位
     *
     * @param query 系统日志岗位查询请求参数
     * @return 系统日志岗位分页信息
     */
    PageResponse<BLogResponse> findPageList(BLogQuery query);
}
