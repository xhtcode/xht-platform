package com.xht.system.modules.dict.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.dict.dao.SysDictItemDao;
import com.xht.system.modules.dict.dao.mapper.SysDictItemMapper;
import com.xht.system.modules.dict.domain.entity.SysDictItemEntity;
import com.xht.system.modules.dict.domain.request.SysDictItemFormRequest;
import com.xht.system.modules.dict.domain.request.SysDictItemQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 系统字典管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysDictItemDaoImpl extends MapperRepositoryImpl<SysDictItemMapper, SysDictItemEntity> implements SysDictItemDao {

    /**
     * 根据更新请求更新指定ID的字典项实体
     *
     * @param formRequest 更新请求
     * @return 更新结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateFormRequest(SysDictItemFormRequest formRequest) {
        // @formatter:off
        LambdaUpdateWrapper<SysDictItemEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper
                .set(condition(formRequest.getDictId()), SysDictItemEntity::getDictId, formRequest.getDictId())
                .set(condition(formRequest.getItemLabel()), SysDictItemEntity::getItemLabel, formRequest.getItemLabel())
                .set(condition(formRequest.getItemValue()), SysDictItemEntity::getItemValue, formRequest.getItemValue())
                .set(condition(formRequest.getItemColor()), SysDictItemEntity::getItemColor, formRequest.getItemColor())
                .set(condition(formRequest.getSortOrder()), SysDictItemEntity::getSortOrder, formRequest.getSortOrder())
                .set(condition(formRequest.getRemark()), SysDictItemEntity::getRemark, formRequest.getRemark())
                .set(condition(formRequest.getStatus()), SysDictItemEntity::getStatus, formRequest.getStatus());
        lambdaUpdateWrapper.eq(SysDictItemEntity::getId, formRequest.getId());
        // @formatter:on
        return update(lambdaUpdateWrapper);
    }


    /**
     * 检查字典项值是否存在
     *
     * @param dictId    字典ID
     * @param itemValue 字典项值
     * @return 存在相同的字典项编码返回true，否则返回false
     */
    @Override
    public Boolean checkDictValue(Long id, Long dictId, String itemValue) {
        LambdaQueryWrapper<SysDictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDictItemEntity::getItemValue, itemValue)
                .eq(SysDictItemEntity::getDictId, dictId)
                .ne(Objects.nonNull(id), SysDictItemEntity::getId, id)
        ;
        return SqlHelper.retBool(count(lambdaQueryWrapper));
    }

    /**
     * 分页查询字典项列表
     *
     * @param page         分页信息
     * @param queryRequest 字典项查询请求参数
     * @return 分页字典项列表
     */
    @Override
    public Page<SysDictItemEntity> queryPageRequest(Page<SysDictItemEntity> page, SysDictItemQueryRequest queryRequest) {
        LambdaQueryWrapper<SysDictItemEntity> queryWrapper = Wrappers.lambdaQuery();
        // @formatter:off
        queryWrapper
                .and(condition(queryRequest.getKeyWord()), wrapper -> wrapper
                        .like(SysDictItemEntity::getDictCode, queryRequest.getKeyWord())
                        .or()
                        .like(SysDictItemEntity::getItemLabel, queryRequest.getKeyWord())
                        .or()
                        .like(SysDictItemEntity::getItemValue, queryRequest.getKeyWord())
                )
                .eq(condition(queryRequest.getDictId()), SysDictItemEntity::getDictId, queryRequest.getDictId())
                .like(condition(queryRequest.getDictCode()), SysDictItemEntity::getDictCode, queryRequest.getDictCode())
                .like(condition(queryRequest.getItemLabel()), SysDictItemEntity::getItemLabel, queryRequest.getItemLabel())
                .like(condition(queryRequest.getItemValue()), SysDictItemEntity::getItemValue, queryRequest.getItemValue())
                .eq(condition(queryRequest.getStatus()), SysDictItemEntity::getStatus, queryRequest.getStatus());
        // @formatter:on
        return page(page, queryWrapper);
    }

    /**
     * 根据字典id查询字典项列表
     *
     * @param dictId 字典id
     * @return 字典项列表
     */
    @Override
    public List<SysDictItemEntity> selectByDictId(Long dictId) {
        return findList(SysDictItemEntity::getDictId, dictId);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysDictItemEntity, ?> getFieldId() {
        return SysDictItemEntity::getId;
    }
}
