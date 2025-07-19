package com.xht.system.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.system.dict.converter.SysDictConverter;
import com.xht.system.dict.domain.entity.SysDictEntity;
import com.xht.system.dict.domain.entity.SysDictItemEntity;
import com.xht.system.dict.domain.request.SysDictFormRequest;
import com.xht.system.dict.domain.request.SysDictQueryRequest;
import com.xht.system.dict.domain.response.SysDictResponse;
import com.xht.system.dict.manager.SysDictItemManager;
import com.xht.system.dict.manager.SysDictManager;
import com.xht.system.dict.service.ISysDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 字典管理Service实现
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl implements ISysDictService {

    private final SysDictManager sysDictManager;

    private final SysDictItemManager sysDictItemManager;
    private final SysDictConverter sysDictConverter;

    /**
     * 创建字典类型
     *
     * @param formRequest 创建请求
     * @return 是否成功
     */
    @Override
    public Boolean create(SysDictFormRequest formRequest) {
        Boolean checkDictCode = sysDictManager.checkDictCode(null, formRequest.getDictCode());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "字典项编码已存在");
        SysDictEntity entity = sysDictConverter.toEntity(formRequest);
        return sysDictManager.saveTransactional(entity);
    }

    /**
     * 删除字典类型
     *
     * @param ids ID列表
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(List<Long> ids) {
        ThrowUtils.notEmpty(ids, "参数错误");
        long dictItemCount = sysDictItemManager.selectCountIn(SysDictItemEntity::getDictId, ids);
        ThrowUtils.throwIf(dictItemCount > 0, BusinessErrorCode.DATA_NOT_EXIST, "所选字典类型下有关联字典项，不能删除");
        return sysDictManager.removeByIds(ids);
    }

    /**
     * 修改字典类型
     *
     * @param formRequest 更新请求
     * @return 是否成功
     */
    @Override
    public Boolean updateById(SysDictFormRequest formRequest) {
        Long id = formRequest.getId();
        ThrowUtils.notNull(id);
        // 检查系统字典管理器中是否存在指定ID的字典项
        // @formatter:off
        SysDictEntity exists = sysDictManager.getOneOpt(new LambdaQueryWrapper<SysDictEntity>()
                .select(SysDictEntity::getId, SysDictEntity::getDictCode, SysDictEntity::getStatus)
                .eq(SysDictEntity::getId, id)).orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST));
        // @formatter:on
        // 检查字典项编码是否存在
        Boolean checkDictCode = sysDictManager.checkDictCode(formRequest.getId(), formRequest.getDictCode());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "字典项编码已存在");
        // 检查是否需要更新字典项状态和code
        boolean updateItemStatus = Objects.equals(exists.getStatus(), formRequest.getStatus()) && Objects.equals(exists.getDictCode(), formRequest.getDictCode());
        return sysDictManager.updateRequest(formRequest, !updateItemStatus);
    }

    /**
     * 获取字典类型详情
     *
     * @param id 字典ID
     * @return 字典详情
     */
    @Override
    public SysDictResponse getById(Long id) {
        return sysDictConverter.toResponse(sysDictManager.getById(id));
    }

    /**
     * 分页查询字典类型
     *
     * @param queryRequest 系统字典查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<SysDictResponse> findPage(SysDictQueryRequest queryRequest) {
        Page<SysDictEntity> page = sysDictManager.queryRequest(queryRequest);
        return sysDictConverter.toResponse(page);
    }


}
