package com.xht.system.modules.log.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.system.modules.log.domian.request.SysLogQueryRequest;
import com.xht.system.modules.log.domian.response.SysLogResponse;

/**
 * 系统日志
 *
 * @author xht
 **/
public interface ISysLogService {

    /**
     * 创建系统日志
     *
     * @param logDTO 系统日志表单请求参数
     * @return 操作结果
     */
    Boolean create(LogDTO logDTO);

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
    PageResponse<SysLogResponse> selectPage(SysLogQueryRequest queryRequest);
}
