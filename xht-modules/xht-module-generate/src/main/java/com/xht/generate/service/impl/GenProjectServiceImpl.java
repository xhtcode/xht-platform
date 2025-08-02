package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenProjectConverter;
import com.xht.generate.dao.GenProjectDao;
import com.xht.generate.domain.entity.GenProjectEntity;
import com.xht.generate.domain.request.GenProjectFormRequest;
import com.xht.generate.domain.request.GenProjectQueryRequest;
import com.xht.generate.domain.response.GenProjectResponse;
import com.xht.generate.service.IGenProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 项目管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenProjectServiceImpl implements IGenProjectService {

    private final GenProjectDao genProjectDao;

    private final GenProjectConverter genProjectConverter;

    /**
     * 创建项目
     *
     * @param formRequest 项目表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenProjectFormRequest formRequest) {
        GenProjectEntity entity = genProjectConverter.toEntity(formRequest);
        return genProjectDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除项目
     *
     * @param id 项目ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        return genProjectDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新项目
     *
     * @param formRequest 项目更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenProjectFormRequest formRequest) {
        Boolean menuExists = genProjectDao.exists(GenProjectEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "项目不存在");
        return genProjectDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    @Override
    public GenProjectResponse getById(Long id) {
        return genProjectConverter.toResponse(genProjectDao.findById(id));
    }

    /**
     * 分页查询项目
     *
     * @param queryRequest 项目查询请求参数
     * @return 项目分页信息
     */
    @Override
    public PageResponse<GenProjectResponse> selectPage(GenProjectQueryRequest queryRequest) {
        Page<GenProjectEntity> page = genProjectDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genProjectConverter.toResponse(page);
    }


                        }
