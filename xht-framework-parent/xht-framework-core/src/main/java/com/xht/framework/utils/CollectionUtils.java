package com.xht.framework.utils;

import java.util.*;

/**
 * 描述： 集合工具类
 *
 * @author xht
 **/
public abstract class CollectionUtils extends org.springframework.util.CollectionUtils {

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptyList()}
     *
     * @param <T>        集合元素类型
     * @param collection 提供的集合，可能为null
     * @return 原集合，若为null返回默认集合
     */
    public static <T> Collection<T> emptyToDefault(Collection<T> collection) {
        return Objects.requireNonNullElseGet(collection, Collections::emptyList);
    }

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptyList()}
     *
     * @param <T>               集合元素类型
     * @param collection        提供的集合，可能为null
     * @param defaultCollection 默认集合，可能为null
     * @return 原集合，若为null返回默认集合
     */
    public static <T> Collection<T> emptyToDefault(Collection<T> collection, Collection<T> defaultCollection) {
        return Objects.requireNonNullElseGet(collection, () -> defaultCollection);
    }


    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptySet()}
     *
     * @param <T> 集合元素类型
     * @param set 提供的集合，可能为null
     * @return 原集合，若为null返回默认集合
     */
    public static <T> Set<T> emptyToDefault(Set<T> set) {
        return Objects.requireNonNullElseGet(set, Collections::emptySet);
    }

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptyList()}
     *
     * @param <T> 集合元素类型
     * @return 原集合，若为null返回默认集合
     */
    public static <T> List<T> emptyToDefault(List<T> list) {
        return Objects.requireNonNullElseGet(list, Collections::emptyList);
    }

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptySet()}
     *
     * @param <T>        集合元素类型
     * @param set        提供的集合，可能为null
     * @param defaultSet 默认集合，可能为null
     * @return 原集合，若为null返回默认集合
     */
    public static <T> Set<T> emptyToDefault(Set<T> set, Set<T> defaultSet) {
        return Objects.requireNonNullElseGet(set, () -> defaultSet);
    }

    /**
     * 如果提供的集合为{@code null}，返回一个不可变的默认空集合，否则返回原集合<br>
     * 空集合使用{@link Collections#emptyList()}
     *
     * @param <T>         集合元素类型
     * @param list        提供的集合，可能为null
     * @param defaultList 默认集合，可能为null
     * @return 原集合，若为null返回默认集合
     */
    public static <T> List<T> emptyToDefault(List<T> list, List<T> defaultList) {
        return Objects.requireNonNullElseGet(list, () -> defaultList);
    }

    /**
     * 对集合按照指定长度分段，每一个段为单独的集合，返回这个集合的列表
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @param size       每个段的长度
     * @return 分段列表
     */
    public static <T> List<List<T>> split(Collection<T> collection, int size) {
        final List<List<T>> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(collection)) {
            return result;
        }
        final int initSize = Math.min(collection.size(), size);
        List<T> subList = new ArrayList<>(initSize);
        for (T t : collection) {
            if (subList.size() >= size) {
                result.add(subList);
                subList = new ArrayList<>(initSize);
            }
            subList.add(t);
        }
        result.add(subList);
        return result;
    }
}
