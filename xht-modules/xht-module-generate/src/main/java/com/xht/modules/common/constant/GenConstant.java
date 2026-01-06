package com.xht.modules.common.constant;

/**
 * @author xht
 **/
public class GenConstant {

    public static final String PATH_SEPARATOR = "/";

    public static final String UNDERLINE = "_";

    public static final String POINT = ".";

    public static final String  COLON = ":";

    /**
     * 输入框
     */
    public static final String INPUT = "input";

    /**
     * 不需要表单字段
     */
    public static final String[] COLUMN_NOT_FORM = {"id","create_by", "create_time", "update_by",
            "update_time", "version", "tenant_id", "del_flag"};


    /**
     * 不需要返回字段
     */
    public static final String[] COLUMN_NOT_LIST = {"id", "version", "tenant_id", "del_flag"};

}
