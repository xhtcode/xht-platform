package com.xht.generate.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.query.GenLogQueryRequest;
import com.xht.generate.domain.response.GenLogResponse;

/**
 * 生成日志管理Service接口
 *
 * @author xht
 **/
public interface IGenLogService {

    /**
     * 根据ID查询生成日志
     *
     * @param id 生成日志ID
     * @return 生成日志信息
     */
    GenLogResponse getById(Long id);

    /**
     * 分页查询生成日志
     *
     * @param queryRequest 生成日志查询请求参数
     * @return 生成日志分页信息
     */
    PageResponse<GenLogResponse> selectPage(GenLogQueryRequest queryRequest);

}
