package com.xht.framework.mybatis.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xht.framework.core.constant.StringConstant;
import com.xht.framework.core.domain.query.BasicQuery;
import com.xht.framework.core.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 描述 ：排序工具类
 * @author xht
 **/
public final class SortTool {

    private SortTool() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 根据查询条件对 QueryWrapper 进行排序设置
     * @param queryWrapper 查询包装器，用于构建查询条件
     * @param query 查询对象，包含排序字段信息
     */
    public static void sort(QueryWrapper<?> queryWrapper, BasicQuery query) {
        List<String> descSort = getDescSort(query);
        List<String> ascSort = getAscSort(query);
        if (!CollectionUtils.isEmpty(descSort)) {
            queryWrapper.orderByDesc(descSort);
        }
        if (!CollectionUtils.isEmpty(ascSort)) {
            queryWrapper.orderByAsc(ascSort);
        }
    }

    /**
     * 根据查询条件对 QueryWrapper 进行排序设置
     * @param queryWrapper 查询包装器，用于构建查询条件
     * @param query 查询对象，包含排序字段信息
     * @param defaultAscSort  默认升序排序字段
     * @param defaultDescSort  默认降序排序字段
     */
    public static <T> void sort(QueryWrapper<T> queryWrapper, BasicQuery query,
                                Supplier<String> defaultAscSort,
                                Supplier<String> defaultDescSort
    ) {
        List<String> descSort = getDescSort(query, defaultAscSort.get());
        List<String> ascSort = getAscSort(query, defaultDescSort.get());
        if (!CollectionUtils.isEmpty(descSort)) {
            queryWrapper.orderByDesc(descSort);
        }
        if (!CollectionUtils.isEmpty(ascSort)) {
            queryWrapper.orderByAsc(ascSort);
        }
    }

    /**
     * 从查询对象中提取升序排序字段，并转换为下划线命名格式的列表
     * @param query 查询对象，包含排序字段信息
     * @return 升序排序字段列表，若无则返回空列表
     */
    public static List<String> getAscSort(BasicQuery query) {
        return getAscSort(query, null);
    }

    /**
     * 从查询对象中提取升序排序字段，并转换为下划线命名格式的列表
     * @param query 查询对象，包含排序字段信息
     * @param defaultAscSort  默认升序排序字段
     * @return 升序排序字段列表，若无则返回空列表
     */
    public static List<String> getAscSort(BasicQuery query, String defaultAscSort) {
        if (Objects.isNull(query)) {
            return null;
        }
        String[] ascArr = StringUtils.delimitedListToStringArray(StringUtils.emptyToDefault(query.getAscName(), defaultAscSort), StringConstant.DELIMITER);
        return arrayToList(ascArr);
    }


    /**
     * 从查询对象中提取降序排序字段，并转换为下划线命名格式的列表
     * @param query 查询对象，包含排序字段信息
     * @return 降序排序字段列表，若无则返回空列表
     */
    public static List<String> getDescSort(BasicQuery query) {
        return getDescSort(query, null);
    }

    /**
     * 从查询对象中提取降序排序字段，并转换为下划线命名格式的列表
     * @param query 查询对象，包含排序字段信息
     * @param descDescSort  默认降序排序字段
     * @return 降序排序字段列表，若无则返回空列表
     */
    public static List<String> getDescSort(BasicQuery query, String descDescSort) {
        if (Objects.isNull(query)) {
            return null;
        }
        String[] descArr = StringUtils.delimitedListToStringArray(StringUtils.emptyToDefault(query.getDescName(), descDescSort), StringConstant.DELIMITER);
        return arrayToList(descArr);
    }

    /**
     * 将字符串数组转换为列表，并将驼峰命名转换为下划线命名
     * @param arr 字符串数组
     * @return 转换后的列表，若数组为空或null则返回空列表
     */
    private static List<String> arrayToList(String[] arr) {
        if (arr == null || arr.length == 0) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>();
        for (String item : arr) {
            list.add(StringUtils.camelToUnderline(item));
        }
        return list;
    }

}
