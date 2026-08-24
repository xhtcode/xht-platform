package com.xht.workflow.flowable.utils;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.StringUtils;
import com.xht.workflow.flowable.common.bo.BpmnPageQueryBO;
import com.xht.workflow.flowable.common.BpmnSupplier;
import org.flowable.common.engine.api.query.Query;

import java.util.List;

/**
 * 描述： 流程查询工具类
 *
 * @author xht
 **/
public class FlowableQueryUtils {

    /**
     * 分页查询
     *
     * @param query         查询对象
     * @param bpmnPageQueryBO 分页查询参数
     * @param function      查询结果转换函数
     * @return 分页查询结果
     */
    public static <T extends Query<?, ?>, U, E> PageResponse<E> findPage(Query<T, U> query, BpmnPageQueryBO bpmnPageQueryBO, BpmnSupplier<List<E>, List<U>> function) {
        int current = bpmnPageQueryBO.getCurrent();
        int size = bpmnPageQueryBO.getSize();
        long totalPage = query.count();
        int maxPageSize = PageTool.totalPageSize(totalPage, size);
        if (current > maxPageSize) {
            current = maxPageSize;
        }
        List<U> models = query.listPage((current - 1) * size, size);
        PageResponse<E> pageResponse = new PageResponse<>();
        pageResponse.setCurrent(current);
        pageResponse.setSize(size);
        pageResponse.setPages(maxPageSize);
        pageResponse.setTotal(totalPage);
        pageResponse.setRecords(function.get(models));
        return pageResponse;
    }

    /**
     * 在sql语句中添加like前缀
     * @param sql 需要添加like前缀的sql
     * @return 添加like前缀后的sql
     */
    public static String appendLikePrefix(String sql) {
        if (StringUtils.hasText(sql)) {
            return "%" + sql + "%";
        }
        return null;
    }
}
