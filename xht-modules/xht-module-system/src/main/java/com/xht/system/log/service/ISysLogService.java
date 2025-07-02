package com.xht.system.log.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.system.log.domian.request.SysLogFormRequest;
import com.xht.system.log.domian.request.SysLogQueryRequest;
import com.xht.system.log.domian.response.SysLogResponse;

/**
 * 系统日志管理
 *
 * @author xht
 **/
public interface ISysLogService {

    /**
     * 创建系统日志
     *
     * @param formRequest 系统日志表单请求参数
     * @return 操作结果
     */
    Boolean create(SysLogFormRequest formRequest);

    /**
     * 获取系统日志详情
     *
     * @param id 系统日志ID
     * @return 系统日志详情
     */
    SysLogResponse getById(Long id);

    /**
     * 分页查询系统日志岗位
     *
     * @param queryRequest 系统日志岗位查询请求参数
     * @return 系统日志岗位分页信息
     */
    PageResponse<SysLogResponse> findPage(SysLogQueryRequest queryRequest);
}
