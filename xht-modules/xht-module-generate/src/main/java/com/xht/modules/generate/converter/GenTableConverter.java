package com.xht.modules.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.generate.domain.bo.TableBo;
import com.xht.modules.generate.domain.entity.GenTableEntity;
import com.xht.modules.generate.domain.form.GenTableInfoForm;
import com.xht.modules.generate.domain.response.GenTableResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 表信息转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTableConverter extends BasicConverter<GenTableEntity, GenTableInfoForm, GenTableResponse> {

    /**
     * 将表信息业务对象转换为表信息响应对象
     *
     * @param tableBo 表信息业务对象，包含从数据库获取的表基本信息，非null
     * @return 转换后的表信息响应对象，用于返回给客户端，非null
     */
    @Mapping(target = "codeName", ignore = true)
    @Mapping(target = "codeComment", ignore = true)
    GenTableResponse toResponse(TableBo tableBo);

    /**
     * 将表信息业务对象转换为表信息响应对象
     *
     * @param tableBoList 表信息业务对象，包含从数据库获取的表基本信息，非null
     * @return 转换后的表信息响应对象，用于返回给客户端，非null
     */
    default List<GenTableResponse> toListResponse(List<TableBo> tableBoList) {
        if (CollectionUtils.isEmpty(tableBoList)) {
            return Collections.emptyList();
        }
        List<GenTableResponse> responseList = new ArrayList<>();
        for (TableBo tableBo : tableBoList) {
            responseList.add(toResponse(tableBo));
        }
        return responseList;
    }

}
