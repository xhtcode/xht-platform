package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenTemplateGroupConverter;
import com.xht.generate.dao.GenTemplateDao;
import com.xht.generate.dao.GenTemplateGroupDao;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.entity.GenTemplateGroupEntity;
import com.xht.generate.domain.form.GenTemplateGroupFormRequest;
import com.xht.generate.domain.query.GenTemplateGroupQueryRequest;
import com.xht.generate.domain.response.GenTemplateGroupResponse;
import com.xht.generate.service.IGenTemplateGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 项目管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTemplateGroupServiceImpl implements IGenTemplateGroupService {

    private final GenTemplateGroupDao genTemplateGroupDao;

    private final GenTemplateGroupConverter genTemplateGroupConverter;

    private final GenTemplateDao genTemplateDao;

    /**
     * 创建项目
     *
     * @param formRequest 项目表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenTemplateGroupFormRequest formRequest) {
        GenTemplateGroupEntity entity = genTemplateGroupConverter.toEntity(formRequest);
        entity.setTemplateCount(0);
        return genTemplateGroupDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除项目
     *
     * @param id 项目ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        Boolean exists = genTemplateDao.exists(GenTemplateEntity::getGroupId, id);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "项目下存在模板，不能删除");
        return genTemplateGroupDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新项目
     *
     * @param formRequest 项目更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenTemplateGroupFormRequest formRequest) {
        Boolean menuExists = genTemplateGroupDao.exists(GenTemplateGroupEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "项目不存在");
        return genTemplateGroupDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    @Override
    public GenTemplateGroupResponse getById(Long id) {
        return genTemplateGroupConverter.toResponse(genTemplateGroupDao.findById(id));
    }

    /**
     * 获取代码生成模板组列表
     *
     * @return 代码生成模板组列表响应结果
     */
    public List<GenTemplateGroupResponse> findAll() {
        List<GenTemplateGroupEntity> entityList = genTemplateGroupDao.findAllBy();
        return genTemplateGroupConverter.toResponse(entityList);
    }


    /**
     * 根据提供的查询请求参数分页查询代码生成模板组信息
     *
     * @param queryRequest 查询参数
     * @return 代码生成模板组列表响应结果
     */
    @Override
    public PageResponse<GenTemplateGroupResponse> selectPage(GenTemplateGroupQueryRequest queryRequest) {
        Page<GenTemplateGroupEntity> page = genTemplateGroupDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genTemplateGroupConverter.toResponse(page);
    }


}
