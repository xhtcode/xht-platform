package com.xht.workflow.flowable.utils;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.flowable.common.BpmnSupplier;
import com.xht.workflow.flowable.common.bo.BpmnOrder;
import com.xht.workflow.flowable.common.bo.BpmnPageQueryBO;
import org.flowable.common.engine.api.query.Query;
import org.flowable.common.engine.api.query.QueryProperty;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 描述： 流程查询工具类
 *
 * @author xht
 **/
public abstract class FlowableQueryUtils {

    /**
     * 填充排序参数
     *
     * @param query        查询对象
     * @param queryBO      分页查询参数
     * @param bpmnSupplier 查询结果转换函数
     */
    public static <T extends Query<?, ?>, U, E extends QueryProperty> void fillOrder(Query<T, U> query, BpmnPageQueryBO queryBO, BpmnSupplier<E, String> bpmnSupplier) {
        List<BpmnOrder> orders = queryBO.getOrders();
        if (!CollectionUtils.isEmpty(orders)) {
            for (BpmnOrder order : orders) {
                ThrowUtils.notNull(order, "排序参数[orders]不能为空");
                String name = order.getName();
                BpmnOrder.BpmnOrderType orderType = Objects.isNull(order.getOrderType()) ? BpmnOrder.BpmnOrderType.ASC : order.getOrderType();
                ThrowUtils.hasText(name, "排序字段不能为空");
                E sortProperty = bpmnSupplier.get(name);
                // 映射前端字段 -> Flowable内置属性
                query.orderBy(sortProperty);
                if (orderType.equals(BpmnOrder.BpmnOrderType.DESC)) {
                    query.desc();
                } else {
                    query.asc();
                }
            }
        }
    }

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
