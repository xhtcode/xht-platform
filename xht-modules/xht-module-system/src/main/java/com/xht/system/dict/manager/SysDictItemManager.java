package com.xht.system.dict.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.framework.mybatis.manager.BasicManager;
import com.xht.system.dict.domain.entity.SysDictItemEntity;
import com.xht.system.dict.domain.request.SysDictItemFormRequest;
import com.xht.system.dict.mapper.SysDictItemMapper;
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
public class SysDictItemManager extends BasicManager<SysDictItemMapper, SysDictItemEntity> {

    /**
     * 根据更新请求更新指定ID的字典项实体
     *
     * @param formRequest 更新请求
     * @return 更新结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateFormRequest(SysDictItemFormRequest formRequest) {
        // @formatter:off
        LambdaUpdateWrapper<SysDictItemEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(SysDictItemEntity::getDictId, formRequest.getDictId())
                .set(SysDictItemEntity::getItemLabel, formRequest.getItemLabel())
                .set(SysDictItemEntity::getItemValue, formRequest.getItemValue())
                .set(SysDictItemEntity::getItemColor, formRequest.getItemColor())
                .set(SysDictItemEntity::getSortOrder, formRequest.getSortOrder())
                .set(SysDictItemEntity::getRemark, formRequest.getRemark())
                .set(SysDictItemEntity::getStatus, formRequest.getStatus());
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
    public Boolean checkDictValue(Long id, Long dictId, String itemValue) {
        LambdaQueryWrapper<SysDictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDictItemEntity::getItemValue, itemValue)
                .eq(SysDictItemEntity::getDictId, dictId)
                .ne(Objects.nonNull(id), SysDictItemEntity::getId, id)
        ;
        return dataExists(count(lambdaQueryWrapper));
    }
}
