package com.xht.framework.core.validation.phone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * 手机号校验
 *
 * @author xht
 **/
public class PhoneValidator implements ConstraintValidator<Phone, String> {


    private static final Pattern PATTERN = Pattern.compile("1(([38]\\d)|(5[^4&&\\d])|(4[579])|(7[0135678]))\\d{8}");


    public boolean isValid(String var1, ConstraintValidatorContext var2) {
        return var1 != null && PATTERN.matcher(var1).matches();
    }
}
