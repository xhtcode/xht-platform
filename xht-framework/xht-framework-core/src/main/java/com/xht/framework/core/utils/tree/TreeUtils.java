package com.xht.framework.core.utils.tree;

import cn.hutool.core.lang.tree.Tree;
import com.xht.framework.core.exception.UtilException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 描述 ：树工具类
 *
 * @author xht
 **/
@SuppressWarnings("unused")
public final class TreeUtils {

    /**
     * 把树给拆解成list集合
     *
     * @param node               {@link Tree<T>}
     * @param includeCurrentNode 否包含当前节点的名称
     * @return {@link List<TreeNode> }
     */
    public static <T> List<INode<T>> dismantle(INode<T> node, boolean includeCurrentNode) {
        if (Objects.isNull(node)) {
            throw new UtilException("树节点不能为null");
        }

        List<INode<T>> result = new ArrayList<>();
        if (includeCurrentNode) {
            result.add(node);
        }

        List<INode<T>> children = node.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (INode<T> child : children) {
                result.addAll(dismantle(child, true));
            }
        }
        return result;
    }


    /**
     * 把树给拆解成list集合
     *
     * @param nodes {@link List<TreeNode>}
     * @return {@link List<TreeNode> }
     */
    public static <T> List<INode<T>> dismantle(List<INode<T>> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            throw new IllegalArgumentException("树节点列表不能为null或空");
        }

        List<INode<T>> result = new ArrayList<>();
        for (INode<T> node : nodes) {
            result.addAll(dismantle(node, true));
        }
        return result;
    }

    public static <T> List<INode<T>> buildList(List<INode<T>> result) {
        if (CollectionUtils.isEmpty(result)) {
            throw new IllegalArgumentException("构建树的节点列表不能为null或空");
        }
        TreeIBuilder<T> of = TreeIBuilder.of();
        return of.appendList(result).buildList(Boolean.TRUE);
    }

    public static <T> List<INode<T>> buildList(List<INode<T>> result, Boolean desc) {
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        TreeIBuilder<T> of = TreeIBuilder.of();
        return of.appendList(result).buildList(desc);
    }
}
