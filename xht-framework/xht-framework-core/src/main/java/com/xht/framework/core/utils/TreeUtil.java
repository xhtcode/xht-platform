package com.xht.framework.core.utils;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 树工具类
 *
 * @author xht
 */
public final class TreeUtil {

    /**
     * 将list合成树
     *
     * @param list           需要合成树的List
     * @param rootCheck      判断E中为根节点的条件，如：x->x.getPId()==-1L , x->x.getParentId()==null,x->x.getParentMenuId()==0
     * @param parentCheck    判断E中为父节点条件，如：(x,y)->x.getId().equals(y.getPId())
     * @param setSubChildren E中设置下级数据方法，如：Menu::setSubMenus
     * @param <E>            泛型实体对象
     * @return 合成好的树
     */
    public static <E> List<E> makeTree(List<E> list, Predicate<E> rootCheck, BiFunction<E, E, Boolean> parentCheck, BiConsumer<E, List<E>> setSubChildren) {
        return list.stream().filter(rootCheck).peek(x -> setSubChildren.accept(x, makeChildren(x, list, parentCheck, setSubChildren))).collect(Collectors.toList());
    }

    /**
     * 递归生成子节点
     *
     * @param parent      父节点
     * @param allData     所有数据
     * @param parentCheck 判断E中为父节点条件，如：(x,y)->x.getId().equals(y.getPId())
     * @param children    子节点
     * @param <E>         泛型实体对象
     * @return 子节点列表
     */
    private static <E> List<E> makeChildren(E parent, List<E> allData, BiFunction<E, E, Boolean> parentCheck, BiConsumer<E, List<E>> children) {
        return allData.stream().filter(x -> parentCheck.apply(parent, x)).peek(x -> children.accept(x, makeChildren(x, allData, parentCheck, children))).collect(Collectors.toList());
    }
}