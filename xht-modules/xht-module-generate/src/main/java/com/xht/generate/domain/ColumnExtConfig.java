package com.xht.generate.domain;

import lombok.Data;

/**
 * @author xht
 **/
@Data
public class ColumnExtConfig {

    /**
     * 表单项 0：否 1：是
     */
    private String formItem;

    /**
     * 表单必填 0：否 1：是
     */
    private String formRequired;

    /**
     * 表单类型
     */
    private String formType;

    /**
     * 表单效验
     */
    private String formValidator;

    /**
     * 列表项 0：否 1：是
     */
    private String gridItem;

    /**
     * 列表排序 0：否 1：是
     */
    private String gridSort;

    /**
     * 查询项 0：否 1：是
     */
    private String queryItem;

    /**
     * 查询方式
     */
    private String queryType;

    /**
     * 查询表单类型
     */
    private String queryFormType;

}
