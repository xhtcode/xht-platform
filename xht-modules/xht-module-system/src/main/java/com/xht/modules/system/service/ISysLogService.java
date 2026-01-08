package com.xht.modules.system.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.api.system.domain.query.SysLogQuery;
import com.xht.api.system.domain.response.SysLogResponse;

/**
 * 系统日志
 *
 * @author xht
 **/
public interface ISysLogService {

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
    SysLogResponse findById(Long id);

    /**
     * 分页查询系统日志岗位
     *
     * @param query 系统日志岗位查询请求参数
     * @return 系统日志岗位分页信息
     */
    PageResponse<SysLogResponse> findPageList(SysLogQuery query);
}
