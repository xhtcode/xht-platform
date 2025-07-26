package com.xht.framework.mybatis.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.core.domain.response.IResponse;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.mybatis.domain.entity.Entity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 基础转换器接口
 *
 * @author xht
 **/
public interface BasicConverter<T extends Entity, Request extends FormRequest, Response extends IResponse> {

    /**
     * 将创建请求对象转换为实体对象。
     *
     * @param formRequest 创建请求对象
     * @return 转换后的实体对象
     */
    T toEntity(Request formRequest);

    /**
     * 将实体对象转换为响应对象
     *
     * @param entity 实体对象，包含从数据库或其他数据源获取的数据
     * @return 转换后的响应对象，用于返回给客户端
     */
    Response toResponse(T entity);

    /**
     * 将实体对象列表转换为响应对象列表
     *
     * @param entityList 实体对象列表，包含多个从数据库或其他数据源获取的实体对象
     * @return 转换后的响应对象列表，用于返回给客户端
     */
    default List<Response> toResponse(List<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        List<Response> list = new ArrayList<>(entityList.size());
        for (T sysDictEntity : entityList) {
            list.add(toResponse(sysDictEntity));
        }
        return list;
    }

    /**
     * 将分页的实体对象转换为分页的响应对象
     *
     * @param page 分页的实体对象，包含当前页的数据以及分页信息
     * @return 转换后的分页响应对象，用于返回给客户端，包含当前页的数据以及分页信息
     */
    default PageResponse<Response> toResponse(Page<T> page) {
        PageResponse<Response> response = new PageResponse<>();
        response.setCurrent(page.getCurrent()); // 设置当前页码
        response.setSize(page.getSize()); // 设置每页显示的数据量
        response.setTotal(page.getTotal()); // 设置总数据量
        response.setPages(page.getPages()); // 设置总页数
        response.setRecords(toResponse(page.getRecords())); // 设置当前页的数据列表
        return response;
    }
}
