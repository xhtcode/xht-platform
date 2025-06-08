package com.xht.framework.core.utils.tree;

import com.xht.framework.core.exception.UtilException;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * 描述 ：树节点父类
 *
 * @author 小糊涂
 **/
@SuppressWarnings("all")
public interface INode<T> extends Comparable<INode<T>>, Serializable {

    /**
     * 获取ID
     *
     * @return ID
     */
    T getId();

    /**
     * 设置ID
     *
     * @param id ID
     * @return this
     */
    INode<T> setId(T id);

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    T getParentId();

    /**
     * 设置父节点ID
     *
     * @param parentId 父节点ID
     * @return this
     */
    INode<T> setParentId(T parentId);

    /**
     * 获取权重
     *
     * @return 权重
     */
    Integer getWeight();

    /**
     * 设置权重
     *
     * @param weight 权重
     * @return this
     */
    INode<T> setWeight(int weight);


    default int compareTo(@NotNull INode<T> node) {
        if (node == null) {
            throw new UtilException("The node to compare with cannot be null.");
        }
        // 假设权重可以为null，所以这里需要处理null的情况，避免空指针异常
        Integer thisWeight = this.getWeight();
        Integer thatWeight = node.getWeight();
        if (thisWeight == null && thatWeight == null) {
            return 0; // 如果两个权重都为null，认为它们相等
        } else if (thisWeight == null) {
            return -1; // 如果当前权重为null，认为比有值的权重小
        } else if (thatWeight == null) {
            return 1; // 如果传入的节点权重为null，认为当前权重大
        }
        return thisWeight.compareTo(thatWeight);
    }

    void addChildren(INode<T> node);

    List<INode<T>> getChildren();

}
