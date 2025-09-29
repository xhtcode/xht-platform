package com.xht.system.modules.dict.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.modules.dict.converter.SysDictConverter;
import com.xht.system.modules.dict.dao.SysDictDao;
import com.xht.system.modules.dict.dao.SysDictItemDao;
import com.xht.system.modules.dict.domain.entity.SysDictEntity;
import com.xht.system.modules.dict.domain.entity.SysDictItemEntity;
import com.xht.system.modules.dict.domain.request.SysDictFormRequest;
import com.xht.system.modules.dict.domain.request.SysDictQueryRequest;
import com.xht.system.modules.dict.domain.response.SysDictResponse;
import com.xht.system.modules.dict.service.ISysDictService;
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

    private final SysDictDao sysDictDao;

    private final SysDictItemDao sysDictItemDao;

    private final SysDictConverter sysDictConverter;

    /**
     * 创建字典类型
     *
     * @param formRequest 创建请求
     * @return 是否成功
     */
    @Override
    public Boolean create(SysDictFormRequest formRequest) {
        Boolean checkDictCode = sysDictDao.checkDictCode(null, formRequest.getDictCode());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "字典项编码已存在");
        SysDictEntity entity = sysDictConverter.toEntity(formRequest);
        return sysDictDao.saveTransactional(entity);
    }

    /**
     * 删除字典类型
     *
     * @param ids ID列表
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeById(List<Long> ids) {
        ThrowUtils.notEmpty(ids, "参数错误");
        long dictItemCount = sysDictItemDao.count(SysDictItemEntity::getDictId, ids);
        ThrowUtils.throwIf(dictItemCount > 0, BusinessErrorCode.DATA_NOT_EXIST, "所选字典类型下有关联字典项，不能删除");
        return sysDictDao.deleteAllById(ids);
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
        SysDictEntity exists = sysDictDao.findOptionalById(id).orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST));
        // @formatter:on
        // 检查字典项编码是否存在
        Boolean checkDictCode = sysDictDao.checkDictCode(formRequest.getId(), formRequest.getDictCode());
        ThrowUtils.throwIf(checkDictCode, BusinessErrorCode.DATA_EXIST, "字典项编码已存在");
        // 检查是否需要更新字典项状态和code
        boolean updateItemStatus = Objects.equals(exists.getStatus(), formRequest.getStatus()) && Objects.equals(exists.getDictCode(), formRequest.getDictCode());
        return sysDictDao.updateRequest(formRequest, !updateItemStatus);
    }

    /**
     * 获取字典类型详情
     *
     * @param id 字典ID
     * @return 字典详情
     */
    @Override
    public SysDictResponse findById(Long id) {
        return sysDictConverter.toResponse(sysDictDao.findById(id));
    }

    /**
     * 分页查询字典类型
     *
     * @param queryRequest 系统字典查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<SysDictResponse> pageList(SysDictQueryRequest queryRequest) {
        Page<SysDictEntity> page = sysDictDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return sysDictConverter.toResponse(page);
    }


}
