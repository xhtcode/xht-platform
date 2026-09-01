package com.xht.workflow.flowable.common.bulder;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.flowable.common.BpmnBuilder;
import com.xht.workflow.flowable.common.bo.BpmnOrder;
import com.xht.workflow.flowable.common.bo.BpmnPageQueryBO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： Bpmn页面查询构建器
 *
 * @author xht
 **/
public abstract class BpmnPageQueryBuilder<T extends BpmnPageQueryBO> implements BpmnBuilder<T> {

    /**
     * 当前页
     */
    protected int current;

    /**
     * 每页显示条数
     */
    protected int size;

    /**
     * 排序参数
     */
    private List<BpmnOrder> orders;
    /**
     * 默认排序参数
     */
    private List<BpmnOrder> defaultOrders;


    public BpmnPageQueryBuilder() {
        this.orders = new ArrayList<>();
        this.defaultOrders = new ArrayList<>();
    }

    /**
     * 设置当前页
     * @param current 当前页
     * @return 构建者本身
     */
    public BpmnPageQueryBuilder<T> current(int current) {
        this.current = current;
        return this;
    }

    /**
     * 设置每页显示条数
     * @param size 每页显示条数
     * @return 构建者本身
     */
    public BpmnPageQueryBuilder<T> size(int size) {
        this.size = size;
        return this;
    }

    /**
     * 设置排序参数
     * @param orders 排序参数
     * @return 构建者本身
     */
    public BpmnPageQueryBuilder<T> orders(List<BpmnOrder> orders) {
        if (!CollectionUtils.isEmpty(orders)) {
            this.orders = orders;
        }
        return this;
    }

    /**
     * 添加排序参数
     * @param order 排序字段
     * @param orderType 排序类型
     * @return 构建者本身
     */
    public BpmnPageQueryBuilder<T> order(String order, BpmnOrder.BpmnOrderType orderType) {
        ThrowUtils.hasText(order, "排序字段不能为空");
        ThrowUtils.notNull(orderType, "排序类型不能为空");
        this.orders.add(new BpmnOrder(order, orderType));
        return this;
    }

    /**
     * 添加排序参数
     * @param ascList 排序字段
     * @return 构建者本身
     */
    public BpmnPageQueryBuilder<T> asc(List<String> ascList) {
        if (CollectionUtils.isEmpty(ascList)) {
            for (String s : ascList) {
                ThrowUtils.hasText(s, "排序字段不能为空");
                this.orders.add(new BpmnOrder(s, BpmnOrder.BpmnOrderType.ASC));
            }
        }
        return this;
    }

    /**
     * 添加排序参数
     * @param descList 排序字段
     * @return 构建者本身
     */
    public BpmnPageQueryBuilder<T> desc(List<String> descList) {
        if (CollectionUtils.isEmpty(descList)) {
            for (String s : descList) {
                ThrowUtils.hasText(s, "排序字段不能为空");
                this.orders.add(new BpmnOrder(s, BpmnOrder.BpmnOrderType.DESC));
            }
        }
        return this;
    }

    public BpmnPageQueryBuilder<T> defaultOrder(String order, BpmnOrder.BpmnOrderType bpmnOrder) {
        ThrowUtils.hasText(order, "排序字段不能为空");
        ThrowUtils.notNull(bpmnOrder, "排序类型不能为空");
        this.defaultOrders.add(new BpmnOrder(order, bpmnOrder));
        return this;
    }

    /**
     * 构建流程模型初始化参数
     */
    @Override
    public T build() {
        T t = createQueryData();
        if (current < 1) {
            current = 1;
        }
        if (size < 1) {
            size = 10;
        }
        t.setCurrent(current);
        t.setSize(size);
        if (!CollectionUtils.isEmpty(orders)) {
            t.setOrders(orders);
        }else {
            t.setOrders(defaultOrders);
        }
        return t;
    }

    /**
     * 填充查询参数
     */
    protected abstract T createQueryData();

}
