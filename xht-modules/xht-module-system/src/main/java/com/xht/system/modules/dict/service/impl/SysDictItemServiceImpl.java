package com.xht.system.modules.dict.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.modules.dict.converter.SysDictItemConverter;
import com.xht.system.modules.dict.dao.SysDictDao;
import com.xht.system.modules.dict.dao.SysDictItemDao;
import com.xht.system.modules.dict.domain.entity.SysDictEntity;
import com.xht.system.modules.dict.domain.entity.SysDictItemEntity;
import com.xht.system.modules.dict.domain.request.SysDictItemFormRequest;
import com.xht.system.modules.dict.domain.request.SysDictItemQueryRequest;
import com.xht.system.modules.dict.domain.response.SysDictItemResponse;
import com.xht.system.modules.dict.domain.vo.SysDictVo;
import com.xht.system.modules.dict.service.ISysDictItemService;
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
     * @param formRequest 系统字典项表单请求参数
     * @return 创建的系统字典项实体
     */
    @Override
    public Boolean create(SysDictItemFormRequest formRequest) {
        Boolean checkDictCode = sysDictItemDao.checkDictValue(null, formRequest.getDictId(), formRequest.getItemValue());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "字典项值已存在");
        SysDictItemEntity entity = sysDictItemConverter.toEntity(formRequest);
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
        return sysDictItemDao.removeByIds(ids);
    }

    /**
     * 根据ID更新系统字典项
     *
     * @param formRequest 系统字典项更新请求参数
     * @return 更新是否成功
     */
    @Override
    public boolean updateById(SysDictItemFormRequest formRequest) {
        Long id = formRequest.getId();
        ThrowUtils.notNull(id);
        // 校验数据是否存在
        boolean exists = sysDictItemDao.exists(SysDictItemEntity::getId, id);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "字典项不存在");
        // 校验字典项值 是否存在
        Boolean checkDictCode = sysDictItemDao.checkDictValue(id, formRequest.getDictId(), formRequest.getItemValue());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "字典项编码已存在");
        return sysDictItemDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID获取系统字典项
     *
     * @param id 系统字典项ID
     * @return 系统字典项响应信息
     */
    @Override
    public SysDictItemResponse getById(Long id) {
        return sysDictItemConverter.toResponse(sysDictItemDao.getById(id));
    }

    /**
     * 分页查询系统字典项
     *
     * @param queryRequest 系统字典项查询请求参数
     * @return 分页响应结果，包含系统字典项响应信息
     */
    @Override
    public PageResponse<SysDictItemResponse> selectPage(SysDictItemQueryRequest queryRequest) {
        if (Objects.isNull(queryRequest.getDictId())) {
            return PageTool.empty();
        }
        Page<SysDictItemEntity> page = sysDictItemDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return sysDictItemConverter.toResponse(page);
    }


    /**
     * 根据字典编码获取系统字典项列表
     *
     * @param dictCode 字典编码
     * @return 系统字典项VO列表
     */
    @Override
    public SysDictVo getByDictCode(String dictCode) {
        SysDictEntity sysDictEntity = sysDictDao.getOneOpt(SysDictEntity::getDictCode, dictCode).orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST));
        SysDictVo sysDictVo = convertEntityToVo(sysDictEntity);
        List<SysDictItemEntity> sysDictItemEntities = sysDictItemDao.selectByDictId(sysDictEntity.getId());
        List<SysDictItemResponse> response = sysDictItemConverter.toResponse(sysDictItemEntities);
        sysDictVo.setItems(response);
        return sysDictVo;
    }

    /**
     * 转换
     */
    private SysDictVo convertEntityToVo(SysDictEntity sysDictEntity) {
        SysDictVo sysDictVo = new SysDictVo();
        sysDictVo.setId(sysDictEntity.getId());
        sysDictVo.setDictCode(sysDictEntity.getDictCode());
        sysDictVo.setDictName(sysDictEntity.getDictName());
        sysDictVo.setSortOrder(sysDictEntity.getSortOrder());
        sysDictVo.setRemark(sysDictEntity.getRemark());
        sysDictVo.setStatus(sysDictEntity.getStatus());
        return sysDictVo;
    }

}
