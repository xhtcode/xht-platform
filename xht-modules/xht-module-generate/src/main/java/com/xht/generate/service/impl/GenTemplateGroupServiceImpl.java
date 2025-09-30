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
import com.xht.generate.domain.form.GenTemplateGroupForm;
import com.xht.generate.domain.query.GenTemplateGroupQuery;
import com.xht.generate.domain.response.GenTemplateGroupResp;
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
     * @param form 项目表单请求参数
     */
    @Override
    public void create(GenTemplateGroupForm form) {
        GenTemplateGroupEntity entity = genTemplateGroupConverter.toEntity(form);
        entity.setTemplateCount(0);
        genTemplateGroupDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除项目
     *
     * @param id 项目ID
     */
    @Override
    public void removeById(Long id) {
        Boolean exists = genTemplateDao.exists(GenTemplateEntity::getGroupId, id);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "项目下存在模板，不能删除");
        genTemplateGroupDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新项目
     *
     * @param form 项目更新请求参数
     */
    @Override
    public void updateById(GenTemplateGroupForm form) {
        Boolean menuExists = genTemplateGroupDao.exists(GenTemplateGroupEntity::getId, form.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "项目不存在");
        genTemplateGroupDao.updateFormRequest(form);
    }

    /**
     * 根据ID查询项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    @Override
    public GenTemplateGroupResp findById(Long id) {
        return genTemplateGroupConverter.toResponse(genTemplateGroupDao.findById(id));
    }

    /**
     * 获取代码生成模板组列表
     *
     * @return 代码生成模板组列表响应结果
     */
    public List<GenTemplateGroupResp> findAll() {
        List<GenTemplateGroupEntity> entityList = genTemplateGroupDao.findAllBy();
        return genTemplateGroupConverter.toResponse(entityList);
    }


    /**
     * 根据提供的查询请求参数分页查询代码生成模板组信息
     *
     * @param query 查询参数
     * @return 代码生成模板组列表响应结果
     */
    @Override
    public PageResponse<GenTemplateGroupResp> pageList(GenTemplateGroupQuery query) {
        Page<GenTemplateGroupEntity> page = genTemplateGroupDao.queryPageRequest(PageTool.getPage(query), query);
        return genTemplateGroupConverter.toResponse(page);
    }


}
