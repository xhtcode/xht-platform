package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenTableInfoConverter;
import com.xht.generate.dao.GenTableInfoDao;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.request.DataBaseQueryRequest;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.request.GenTableInfoQueryRequest;
import com.xht.generate.domain.request.ImportTableFormRequest;
import com.xht.generate.domain.response.GenTableInfoResponse;
import com.xht.generate.service.IGenTableInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 表信息管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTableInfoServiceImpl implements IGenTableInfoService {

    private final GenTableInfoDao genTableInfoDao;

    private final GenTableInfoConverter genTableInfoConverter;

    /**
     * 导入表
     *
     * @param formRequest 表信息表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean importTable(ImportTableFormRequest formRequest) {
        return Boolean.TRUE;
    }

    /**
     * 根据ID删除表信息
     *
     * @param id 表信息ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        return genTableInfoDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新表信息
     *
     * @param formRequest 表信息更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenTableInfoFormRequest formRequest) {
        Boolean menuExists = genTableInfoDao.exists(GenTableInfoEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "表信息不存在");
        return genTableInfoDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询表信息
     *
     * @param id 表信息ID
     * @return 表信息信息
     */
    @Override
    public GenTableInfoResponse getById(Long id) {
        return genTableInfoConverter.toResponse(genTableInfoDao.findById(id));
    }


    /**
     * 分页查询已存在的表信息
     *
     * @param queryRequest 查询条件封装对象，包含分页参数和查询条件
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Override
    public PageResponse<GenTableInfoResponse> selectExistsPage(GenTableInfoQueryRequest queryRequest) {
        Page<GenTableInfoEntity> page = genTableInfoDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genTableInfoConverter.toResponse(page);
    }

    /**
     * 分页查询不存在的表信息
     *
     * @param queryRequest 数据库查询条件封装对象，包含分页参数和数据库连接信息
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Override
    public PageResponse<GenTableInfoResponse> selectNoExistsPage(DataBaseQueryRequest queryRequest) {
        return null;
    }


}
