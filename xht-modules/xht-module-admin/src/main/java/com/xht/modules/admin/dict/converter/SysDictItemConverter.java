package com.xht.modules.admin.dict.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.dict.domain.form.SysDictItemForm;
import com.xht.modules.admin.dict.domain.response.SysDictItemResponse;
import com.xht.platform.common.dict.domain.vo.DictVo;
import com.xht.modules.admin.dict.entity.SysDictItemEntity;
import com.xht.modules.admin.dict.enums.DictStatusEnums;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 字典项转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictItemConverter extends BasicConverter<SysDictItemEntity, SysDictItemForm, SysDictItemResponse> {

    /**
     * 字典项实体转换成字典项视图对象
     *
     * @param dictItemEntities 字典项实体
     * @return 字典项视图对象
     */
    @Mapping(source = "itemLabel", target = "label")
    @Mapping(source = "itemValue", target = "value")
    @Mapping(source = "itemColor", target = "color")
    @Mapping(source = "status", target = "disabled", qualifiedByName = "statusToDisabled")
    DictVo toVo(SysDictItemEntity dictItemEntities);

    /**
     * 字典项实体转换成字典项视图对象列表
     *
     * @param dictItemEntities 字典项实体列表
     * @return 字典项视图对象列表
     */
    default List<DictVo> toVo(List<SysDictItemEntity> dictItemEntities) {
        if (CollectionUtils.isEmpty(dictItemEntities)) {
            return List.of();
        }
        return dictItemEntities.stream().map(this::toVo).collect(Collectors.toList());
    }

    @Named("statusToDisabled")
    default Boolean statusToDisabled(DictStatusEnums status) {
        return Objects.equals(status, DictStatusEnums.DISABLE);
    }


}
