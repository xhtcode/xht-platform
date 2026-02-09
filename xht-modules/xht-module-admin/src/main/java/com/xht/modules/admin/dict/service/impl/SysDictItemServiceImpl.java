package com.xht.modules.admin.dict.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.modules.admin.dict.converter.SysDictItemConverter;
import com.xht.modules.admin.dict.dao.SysDictDao;
import com.xht.modules.admin.dict.dao.SysDictItemDao;
import com.xht.modules.admin.dict.domain.form.SysDictItemForm;
import com.xht.modules.admin.dict.domain.query.SysDictItemQuery;
import com.xht.modules.admin.dict.domain.response.SysDictItemResponse;
import com.xht.platform.common.dict.domain.vo.DictVo;
import com.xht.modules.admin.dict.entity.SysDictEntity;
import com.xht.modules.admin.dict.entity.SysDictItemEntity;
import com.xht.modules.admin.dict.enums.DictShowDisabledEnums;
import com.xht.modules.admin.dict.enums.DictStatusEnums;
import com.xht.modules.admin.dict.service.ISysDictItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 系统字典项Service实现
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl implements ISysDictItemService {

    private final SysDictDao sysDictDao;

    private final SysDictItemDao sysDictItemDao;

    private final SysDictItemConverter sysDictItemConverter;

    /**
     * 根据创建请求创建系统字典项
     *
     * @param form 系统字典项表单请求参数
     * @return 创建的系统字典项实体
     */
    @Override
    public Boolean create(SysDictItemForm form) {
        Long dictId = form.getDictId();
        SysDictEntity dictEntity = sysDictDao.findById(dictId);
        ThrowUtils.throwIf(Objects.isNull(dictEntity), BusinessErrorCode.DATA_NOT_EXIST, "字典不存在");
        ThrowUtils.throwIf(!Objects.equals(DictStatusEnums.ENABLE, dictEntity.getStatus()), BusinessErrorCode.DATA_NOT_EXIST, "字典不存在");
        Boolean checkDictCode = sysDictItemDao.checkDictValue(null, dictId, form.getItemLabel());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "字典项值已存在");
        SysDictItemEntity entity = sysDictItemConverter.toEntity(form);
        entity.setDictCode(dictEntity.getDictCode());
        return sysDictItemDao.saveTransactional(entity);
    }

    /**
     * 根据ID删除系统字典项
     *
     * @param ids 系统字典项ID
     * @return 删除是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(List<Long> ids) {
        return sysDictItemDao.removeAllById(ids);
    }

    /**
     * 根据ID更新系统字典项
     *
     * @param form 系统字典项更新请求参数
     * @return 更新是否成功
     */
    @Override
    public boolean updateById(SysDictItemForm form) {
        Long id = form.getId();
        // 校验数据是否存在
        boolean exists = sysDictItemDao.exists(SysDictItemEntity::getId, id);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "字典项不存在");
        Long dictId = form.getDictId();
        SysDictEntity dictEntity = sysDictDao.findById(dictId);
        ThrowUtils.throwIf(Objects.isNull(dictEntity), BusinessErrorCode.DATA_NOT_EXIST, "字典不存在");
        ThrowUtils.throwIf(!Objects.equals(DictStatusEnums.ENABLE, dictEntity.getStatus()), BusinessErrorCode.DATA_NOT_EXIST, "字典不存在");
        // 校验字典项值 是否存在
        Boolean checkDictCode = sysDictItemDao.checkDictValue(id, dictId, form.getItemLabel());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "字典项编码已存在");
        return sysDictItemDao.updateFormRequest(form, dictEntity.getDictCode());
    }

    /**
     * 根据ID获取系统字典项
     *
     * @param id 系统字典项ID
     * @return 系统字典项响应信息
     */
    @Override
    public SysDictItemResponse findById(Long id) {
        return sysDictItemConverter.toResponse(sysDictItemDao.findById(id));
    }

    /**
     * 分页查询系统字典项
     *
     * @param query 系统字典项查询请求参数
     * @return 分页响应结果，包含系统字典项响应信息
     */
    @Override
    public PageResponse<SysDictItemResponse> findPageList(SysDictItemQuery query) {
        Page<SysDictItemEntity> page = sysDictItemDao.findPageList(PageTool.getPage(query), query);
        return sysDictItemConverter.toResponse(page);
    }


    /**
     * 根据字典编码获取系统字典项列表
     *
     * @param dictCode 字典编码
     * @return 系统字典项VO列表
     */
    @Override
    public List<DictVo> getByDictCode(String dictCode) {
        SysDictEntity sysDictEntity = sysDictDao.findOneOptional(SysDictEntity::getDictCode, dictCode)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST));
        DictShowDisabledEnums showDisabled = sysDictEntity.getShowDisabled();
        DictStatusEnums dictStatusEnums = Objects.equals(DictShowDisabledEnums.SHOW, showDisabled) ? null : DictStatusEnums.ENABLE;
        List<SysDictItemEntity> dictItemEntities = sysDictItemDao.findByDictCode(dictCode, dictStatusEnums);
        return sysDictItemConverter.toVo(dictItemEntities);
    }

}
