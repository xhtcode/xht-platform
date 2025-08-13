package com.xht.system.modules.dict.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.dict.dao.SysDictDao;
import com.xht.system.modules.dict.dao.mapper.SysDictItemMapper;
import com.xht.system.modules.dict.dao.mapper.SysDictMapper;
import com.xht.system.modules.dict.domain.entity.SysDictEntity;
import com.xht.system.modules.dict.domain.entity.SysDictItemEntity;
import com.xht.system.modules.dict.domain.request.SysDictFormRequest;
import com.xht.system.modules.dict.domain.request.SysDictQueryRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 系统字典管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysDictDaoImpl extends MapperRepositoryImpl<SysDictMapper, SysDictEntity> implements SysDictDao {

    @Resource
    private SysDictItemMapper sysDictItemMapper;

    /**
     * 修改系统字典
     *
     * @param formRequest      系统字典修改参数
     * @param updateItemStatus 是否更新字典项状态
     * @return 修改系统字典
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateRequest(SysDictFormRequest formRequest, boolean updateItemStatus) {
        LambdaUpdateWrapper<SysDictEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(condition(formRequest.getDictCode()), SysDictEntity::getDictCode, formRequest.getDictCode())
                .set(condition(formRequest.getDictName()), SysDictEntity::getDictName, formRequest.getDictName())
                .set(condition(formRequest.getSortOrder()), SysDictEntity::getSortOrder, formRequest.getSortOrder())
                .set(condition(formRequest.getRemark()), SysDictEntity::getRemark, formRequest.getRemark())
                .set(condition(formRequest.getStatus()), SysDictEntity::getStatus, formRequest.getStatus())
                .eq(SysDictEntity::getId, formRequest.getId());
        //@formatter:on
        if (updateItemStatus) {
            LambdaUpdateWrapper<SysDictItemEntity> itemEntityWrapper = new LambdaUpdateWrapper<>();
            itemEntityWrapper
                    .set(SysDictItemEntity::getStatus, formRequest.getStatus())
                    .set(SysDictItemEntity::getDictCode, formRequest.getDictCode())
                    .eq(SysDictItemEntity::getDictId, formRequest.getId());
            sysDictItemMapper.update(itemEntityWrapper);
        }
        return update(updateWrapper);
    }

    /**
     * 根据字典ID和字典项编码检查是否存在相同的字典项编码
     *
     * @param dictId   字典id
     * @param dictCode 字典code
     * @return 存在相同的字典项编码返回true，否则返回false
     */
    @Override
    public Boolean checkDictCode(Long dictId, String dictCode) {
        //@formatter:off
        LambdaQueryWrapper<SysDictEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SysDictEntity::getDictCode, dictCode)
                .ne(Objects.nonNull(dictId), SysDictEntity::getId, dictId);
        //@formatter:on
        return SqlHelper.retBool(count(lambdaQueryWrapper));
    }

    /**
     * 查询系统字典列表
     *
     * @param queryRequest 系统字典查询参数
     * @return 系统字典列表
     */
    @Override
    public Page<SysDictEntity> queryPageRequest(Page<SysDictEntity> page, SysDictQueryRequest queryRequest) {
        LambdaQueryWrapper<SysDictEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(condition(queryRequest.getKeyWord()), wrapper -> wrapper
                .like(SysDictEntity::getDictCode, queryRequest.getKeyWord())
                .or()
                .like(SysDictEntity::getDictName, queryRequest.getKeyWord())
        );
        queryWrapper.like(condition(queryRequest.getDictCode()), SysDictEntity::getDictCode, queryRequest.getDictCode());
        queryWrapper.like(condition(queryRequest.getDictName()), SysDictEntity::getDictName, queryRequest.getDictName());
        queryWrapper.eq(condition(queryRequest.getStatus()), SysDictEntity::getStatus, queryRequest.getStatus());
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysDictEntity, ?> getFieldId() {
        return SysDictEntity::getId;
    }
}
