package com.xht.framework.mybatis.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.core.domain.response.IResponse;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.mybatis.domain.entity.Entity;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 基础转换器接口，用于实体与请求/响应对象之间的转换
 *
 * @param <T>       实体类型，继承自Entity
 * @param <Request> 请求对象类型，继承自FormRequest
 * @param <Response> 响应对象类型，继承自IResponse
 * @author xht
 */
public interface BasicConverter<T extends Entity, Request extends FormRequest, Response extends IResponse> {

    /**
     * 将创建请求对象转换为实体对象
     *
     * @param formRequest 创建请求对象，非null
     * @return 转换后的实体对象，非null
     */
    T toEntity(Request formRequest);

    /**
     * 将实体对象转换为响应对象
     *
     * @param entity 实体对象，包含从数据库获取的数据，非null
     * @return 转换后的响应对象，用于返回给客户端，非null
     */
    Response toResponse(T entity);

    /**
     * 将实体对象列表转换为响应对象列表
     *
     * @param entityList 实体对象列表，可为null或空
     * @return 转换后的响应对象列表，非null（空列表而非null）
     */
    default List<Response> toResponse(List<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return List.of(); // 使用Java 9+的不可变空列表
        }

        return entityList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将分页的实体对象转换为分页的响应对象
     *
     * @param page 分页的实体对象，包含当前页数据及分页信息，非null
     * @return 转换后的分页响应对象，包含当前页数据及分页信息，非null
     */
    default PageResponse<Response> toResponse(Page<T> page) {
        PageResponse<Response> response = new PageResponse<>();
        response.setCurrent(page.getCurrent());
        response.setSize(page.getSize());
        response.setTotal(page.getTotal());
        response.setPages(page.getPages());
        response.setRecords(toResponse(page.getRecords()));
        return response;
    }

}
