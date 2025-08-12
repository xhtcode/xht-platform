package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenLogConverter;
import com.xht.generate.dao.GenLogDao;
import com.xht.generate.domain.entity.GenLogEntity;
import com.xht.generate.domain.request.GenLogQueryRequest;
import com.xht.generate.domain.response.GenLogResponse;
import com.xht.generate.service.IGenLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 生成日志管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenLogServiceImpl implements IGenLogService {

    private final GenLogDao genLogDao;

    private final GenLogConverter genLogConverter;

    /**
     * 根据ID查询生成日志
     *
     * @param id 生成日志ID
     * @return 生成日志信息
     */
    @Override
    public GenLogResponse getById(Long id) {
        return genLogConverter.convert(genLogDao.findById(id));
    }

    /**
     * 分页查询生成日志
     *
     * @param queryRequest 生成日志查询请求参数
     * @return 生成日志分页信息
     */
    @Override
    public PageResponse<GenLogResponse> selectPage(GenLogQueryRequest queryRequest) {
        Page<GenLogEntity> page = genLogDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return toResponse(page);
    }

    /**
     * 将分页的实体对象转换为分页的响应对象
     *
     * @param page 分页的实体对象，包含当前页的数据以及分页信息
     * @return 转换后的分页响应对象，用于返回给客户端，包含当前页的数据以及分页信息
     */
    private PageResponse<GenLogResponse> toResponse(Page<GenLogEntity> page) {
        PageResponse<GenLogResponse> response = new PageResponse<>();
        response.setCurrent(page.getCurrent()); // 设置当前页码
        response.setSize(page.getSize()); // 设置每页显示的数据量
        response.setTotal(page.getTotal()); // 设置总数据量
        response.setPages(page.getPages()); // 设置总页数
        response.setRecords(genLogConverter.convert(page.getRecords())); // 设置当前页的数据列表
        return response;
    }

}
