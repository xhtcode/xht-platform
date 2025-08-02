package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenLogConverter;
import com.xht.generate.dao.GenLogDao;
import com.xht.generate.domain.entity.GenLogEntity;
import com.xht.generate.domain.request.GenLogFormRequest;
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
     * 创建生成日志
     *
     * @param formRequest 生成日志表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenLogFormRequest formRequest) {
        GenLogEntity entity = genLogConverter.toEntity(formRequest);
        return genLogDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除生成日志
     *
     * @param id 生成日志ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        return genLogDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新生成日志
     *
     * @param formRequest 生成日志更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenLogFormRequest formRequest) {
        Boolean menuExists = genLogDao.exists(GenLogEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "生成日志不存在");
        return genLogDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询生成日志
     *
     * @param id 生成日志ID
     * @return 生成日志信息
     */
    @Override
    public GenLogResponse getById(Long id) {
        return genLogConverter.toResponse(genLogDao.findById(id));
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
        return genLogConverter.toResponse(page);
    }


                        }
