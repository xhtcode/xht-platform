package com.xht.system.dict.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.manager.BasicManager;
import com.xht.system.dict.domain.entity.SysDictEntity;
import com.xht.system.dict.domain.entity.SysDictItemEntity;
import com.xht.system.dict.domain.request.SysDictFormRequest;
import com.xht.system.dict.domain.request.SysDictQueryRequest;
import com.xht.system.dict.mapper.SysDictItemMapper;
import com.xht.system.dict.mapper.SysDictMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 系统字典管理
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysDictManager extends BasicManager<SysDictMapper, SysDictEntity> {

    @Resource
    private SysDictItemMapper sysDictItemMapper;

    /**
     * 构建查询Wrapper
     *
     * @param queryRequest 系统字典查询参数
     * @return 构建好的LambdaQueryWrapper
     */
    private LambdaQueryWrapper<SysDictEntity> buildQueryWrapper(SysDictQueryRequest queryRequest) {
        boolean keyWordExists = StringUtils.hasLength(queryRequest.getKeyWord());
        LambdaQueryWrapper<SysDictEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (keyWordExists) {
            queryWrapper.and(wrapper -> wrapper
                    .like(SysDictEntity::getDictCode, queryRequest.getKeyWord())
                    .or()
                    .like(SysDictEntity::getDictName, queryRequest.getKeyWord())
            );
        }
        queryWrapper.like(StringUtils.hasLength(queryRequest.getDictCode()), SysDictEntity::getDictCode, queryRequest.getDictCode());
        queryWrapper.like(StringUtils.hasLength(queryRequest.getDictName()), SysDictEntity::getDictName, queryRequest.getDictName());
        queryWrapper.eq(Objects.nonNull(queryRequest.getStatus()), SysDictEntity::getStatus, queryRequest.getStatus());
        return queryWrapper;
    }


    /**
     * 修改系统字典
     *
     * @param formRequest      系统字典修改参数
     * @param updateItemStatus 是否更新字典项状态
     * @return 修改系统字典
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean formRequest(SysDictFormRequest formRequest, boolean updateItemStatus) {
        LambdaUpdateWrapper<SysDictEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(SysDictEntity::getDictCode, formRequest.getDictCode())
                .set(SysDictEntity::getDictName, formRequest.getDictName())
                .set(SysDictEntity::getSortOrder, formRequest.getSortOrder())
                .set(SysDictEntity::getRemark, formRequest.getRemark())
                .set(SysDictEntity::getStatus, formRequest.getStatus())
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
    public Boolean checkDictCode(Long dictId, String dictCode) {
        //@formatter:off
        LambdaQueryWrapper<SysDictEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SysDictEntity::getDictCode, dictCode)
                .ne(Objects.nonNull(dictId), SysDictEntity::getId, dictId);
        //@formatter:on
        return dataExists(count(lambdaQueryWrapper));
    }
}
