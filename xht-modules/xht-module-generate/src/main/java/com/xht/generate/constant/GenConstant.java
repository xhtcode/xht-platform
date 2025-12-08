package com.xht.generate.constant;

/**
 * @author xht
 **/
public class GenConstant {

    public static final String PATH_SEPARATOR = "/";

    public static final String UNDERLINE = "_";

    public static final String POINT = ".";

    public static final String  COLON = ":";
    /**
     * 等于
     */
    public static final String EQ = "eq";


    /**
     * 输入框
     */
    public static final String INPUT = "input";

    /**
     * 不需要表单字段
     */
    public static final String[] COLUMN_NOT_FORM = {"create_by", "create_time", "update_by",
            "update_time", "del_flag", "version", "tenant_id"};


    /**
     * 不需要返回字段
     */
    public static final String[] COLUMN_NOT_LIST = {"create_by", "create_time", "update_by",
            "update_time", "del_flag", "version", "tenant_id"};


    public static final String EMPTY = "";
}
