package com.xht.platform.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.file.converter.SysFileTypeConverter;
import com.xht.platform.file.dao.SysFileTypeDao;
import com.xht.platform.file.domain.form.SysFileTypeForm;
import com.xht.platform.file.domain.query.SysFileTypeQuery;
import com.xht.platform.file.domain.response.SysFileTypeResponse;
import com.xht.platform.file.entity.SysFileTypeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述： 文件预览类型配置服务实现
 * <p>
 * 提供文件预览类型配置（sys_file_type）的元数据 CRUD；创建/更新前校验文件后缀唯一
 * （非空时不允许重复，唯一约束对应 uk_suffix），删除/更新前校验配置存在
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileTypeServiceImpl implements ISysFileTypeService {

    private final SysFileTypeDao sysFileTypeDao;

    private final SysFileTypeConverter sysFileTypeConverter;

    /**
     * 创建文件预览类型配置
     *
     * @param form 文件预览类型配置表单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysFileTypeForm form) {
        checkSuffixUnique(null, form.getFileSuffix());
        sysFileTypeDao.save(sysFileTypeConverter.toEntity(form));
    }

    /**
     * 根据ID删除文件预览类型配置
     *
     * @param fileTypeId 配置ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long fileTypeId) {
        AssertUtils.notNull(fileTypeId, BusinessErrorCode.PARAM_ERROR);
        SysFileTypeEntity sysFileTypeEntity = requireExist(fileTypeId);
        sysFileTypeDao.removeCache(sysFileTypeEntity.getFileSuffix());
        sysFileTypeDao.removeById(fileTypeId);
    }

    /**
     * 根据文件后缀删除缓存
     *
     * @param fileSuffix 文件后缀
     */
    @Override
    public void removeCache(String fileSuffix) {
        sysFileTypeDao.removeCache(fileSuffix);
    }

    /**
     * 根据ID更新文件预览类型配置
     *
     * @param fileTypeId 配置ID
     * @param form       文件预览类型配置表单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Long fileTypeId, SysFileTypeForm form) {
        AssertUtils.notNull(fileTypeId, BusinessErrorCode.PARAM_ERROR);
        SysFileTypeEntity sysFileTypeEntity = requireExist(fileTypeId);
        sysFileTypeDao.removeCache(sysFileTypeEntity.getFileSuffix());
        checkSuffixUnique(fileTypeId, form.getFileSuffix());
        sysFileTypeDao.updateFormRequest(fileTypeId, form);
    }

    /**
     * 根据ID查询文件预览类型配置
     *
     * @param fileTypeId 配置ID
     * @return 文件预览类型配置
     */
    @Override
    public SysFileTypeResponse findById(Long fileTypeId) {
        AssertUtils.notNull(fileTypeId, BusinessErrorCode.PARAM_ERROR);
        return sysFileTypeConverter.toResponse(requireExist(fileTypeId));
    }

    /**
     * 分页查询文件预览类型配置
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<SysFileTypeResponse> findPageList(SysFileTypeQuery query) {
        Page<SysFileTypeEntity> page = sysFileTypeDao.findPageList(PageTool.getPage(query), query);
        return sysFileTypeConverter.toResponse(page);
    }

    /**
     * 校验配置存在，返回配置实体
     *
     * @param fileTypeId 配置ID
     * @return 配置实体
     */
    private SysFileTypeEntity requireExist(Long fileTypeId) {
        SysFileTypeEntity entity = sysFileTypeDao.findById(fileTypeId);
        AssertUtils.notNull(entity, BusinessErrorCode.FILE_TYPE_NOT_EXIST);
        return entity;
    }

    /**
     * 校验文件后缀唯一（非空时不允许与已有配置冲突）
     *
     * @param excludeId  需要排除的配置ID（更新场景），可为 null
     * @param fileSuffix 文件后缀
     */
    private void checkSuffixUnique(Long excludeId, String fileSuffix) {
        AssertUtils.isTrue(!sysFileTypeDao.existsBySuffix(fileSuffix, excludeId), BusinessErrorCode.FILE_TYPE_SUFFIX_EXIST);
    }

}
