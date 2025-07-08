package com.xht.framework.core.constant;

import java.util.regex.Pattern;

/**
 * 正则表达式常量
 *
 * @author xht
 **/
public final class PatternConstant {

    /**
     * 手机号
     */
    public static final Pattern MOBILE_PHONE = Pattern.compile(RegexConstant.MOBILE_PHONE);

    /**
     * 身份证号(1代,15位数字)
     */
    public final static Pattern ID_CARD_15 = Pattern.compile(RegexConstant.ID_CARD_15);

    /**
     * 18位身份证号码
     */
    public final static Pattern ID_CARD_18 = Pattern.compile(RegexConstant.ID_CARD_18);

    /**
     * 18位身份证号码
     */
    public final static Pattern ID_CARD = Pattern.compile(RegexConstant.ID_CARD);

    /**
     * 邮政编码
     */
    public final static Pattern POSTAL_CODE = Pattern.compile(RegexConstant.POSTAL_CODE);

    /**
     * 部门编码
     */
    public final static Pattern DEPT_CODE = Pattern.compile(RegexConstant.DEPT_CODE);

}
