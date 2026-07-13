package com.xht.framework.mybatis.domain;

import com.xht.framework.mybatis.utils.PageTool;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 描述：分页相关
 *
 * @author xht
 **/
@Getter
public class XhtPage<T> implements Serializable {

    /**
     * 当前页
     */
    private final Number current;

    /**
     * 每页显示条数，默认 10
     */
    private final Number size;

    /**
     * 总数
     */
    private final Number total;

    /**
     * 总页数
     */
    private final Number totalPage;

    /**
     * 查询数据列表
     */
    private final List<T> records;

    public XhtPage(Number current, Number size, Number total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.records = records;
        this.totalPage = PageTool.totalPage(this.total, this.size);
    }

    public static <T> Builder<T> builder(List<T> records) {
        return new Builder<>(records);
    }

    public static class Builder<T> {

        /**
         * 当前页
         */
        private Number current = 1;

        /**
         * 每页显示条数，默认 10
         */
        private Number size = 10;

        /**
         * 总数
         */
        private Number total = 0;

        /**
         * 查询数据列表
         */
        private final List<T> records;

        /**
         * 构造方法
         *
         * @param records 查询数据列表
         */
        public Builder(List<T> records) {
            this.records = Objects.requireNonNullElseGet(records, Collections::emptyList);
        }

        /**
         * 设置当前页
         *
         * @param current 当前页
         * @return this
         */
        public Builder<T> current(Number current) {
            this.current = current;
            return this;
        }

        /**
         * 设置每页显示条数
         *
         * @param size 每页显示条数
         * @return this
         */
        public Builder<T> size(Number size) {
            this.size = size;
            return this;
        }

        /**
         * 设置总数
         *
         * @param total 总数
         * @return this
         */
        public Builder<T> total(Number total) {
            this.total = total;
            return this;
        }

        /**
         * 构建分页对象
         *
         * @return 分页对象
         */
        public XhtPage<T> build() {
            this.current = checkValue(this.current, 0);
            this.size = checkValue(this.size, 10);
            this.total = checkValue(this.total, 0);
            return new XhtPage<>(current, size, total, records);
        }

        /**
         * 检查值
         *
         * @param value        值
         * @param defaultValue 默认值
         * @return 检查后的值
         */
        private Number checkValue(Number value, Number defaultValue) {
            return Optional.ofNullable(value).filter(v -> v.longValue() > 0).orElse(defaultValue);
        }

    }


}
