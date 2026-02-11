package com.xht.framework.core.support.dict.validation;

import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.core.support.dict.ISysDictFactory;
import com.xht.framework.core.support.dict.domain.DictVo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Objects;

/**
 * 系统字典校验
 *
 * @author xht
 **/
public class DictValidator implements ConstraintValidator<Dict, String> {

    private String dictCode;

    @Override
    public void initialize(Dict dict) {
        this.dictCode = dict.value();
    }

    /**
     * 校验字典项
     *
     * @param var1 字典项值
     * @param var2 验证上下文
     * @return 校验结果
     */
    @Override
    public boolean isValid(String var1, ConstraintValidatorContext var2) {
        List<DictVo> dictList = SpringContextUtils.getBean(ISysDictFactory.class).getDictList(dictCode);
        for (DictVo dictVo : dictList) {
            if (dictVo.getDisabled() && Objects.equals(dictVo.getValue(), var1)) {
                return true;
            }
        }
        return false;
    }

}
