package com.xht.platform.dict.validation;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.utils.CollectionUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.spring.SpringContextUtils;
import com.xht.platform.common.dict.domain.DictVO;
import com.xht.platform.common.dict.factory.ISysDictFactory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统字典校验
 * <p>
 * 校验规则：值为空时放行，交由 {@code @NotBlank} 处理；值必须命中字典项，且该字典项未被禁用
 * （{@code disabled} 为 {@code null} 时视为未禁用）。
 * <p>
 * 字典门面由 {@code xht-platform-client-dict} 的 {@code DictAutoConfiguration} 注册，
 * 若容器中取不到则显式抛异常，避免静默放行或误报"字典项不存在"。
 *
 * @author xht
 **/
public class DictValidator implements ConstraintValidator<Dict, String> {

    /**
     * 未找到字典门面
     */
    private static final String FACTORY_ABSENT_MESSAGE =
            "字典校验无法执行：容器中未找到 ISysDictFactory，请确认已引入 xht-platform-client-dict 且 DictAutoConfiguration 未被排除";

    /**
     * 字典编码
     */
    private String dictCode;

    @Override
    public void initialize(Dict dict) {
        this.dictCode = dict.value();
    }

    /**
     * 校验字典项
     *
     * @param var1 字典项编码
     * @param var2 验证上下文
     * @return 校验结果
     */
    @Override
    public boolean isValid(String var1, ConstraintValidatorContext var2) {
        // 空值交给 @NotBlank / @NotNull 处理，避免与必填校验重复报错
        if (StringUtils.isEmpty(var1)) {
            return true;
        }
        List<DictVO> dictList = getSysDictFactory().getDictList(dictCode);
        if (CollectionUtils.isEmpty(dictList)) {
            return false;
        }
        for (DictVO dictVo : dictList) {
            // disabled 为 null 视为未禁用；只有「值相等且未禁用」的字典项才通过校验
            if (Objects.equals(dictVo.getValue(), var1) && !Boolean.TRUE.equals(dictVo.getDisabled())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取字典门面
     * <p>
     * 存在多个实现时交由容器按 {@code @Primary} 选择，与 2.8.4 的本地门面约定保持一致。
     *
     * @return 字典门面
     */
    private ISysDictFactory getSysDictFactory() {
        Map<String, ISysDictFactory> factoryMap = SpringContextUtils.getBeansOfType(ISysDictFactory.class);
        if (factoryMap.isEmpty()) {
            throw new BusinessException(FACTORY_ABSENT_MESSAGE);
        }
        return factoryMap.size() == 1
                ? factoryMap.values().iterator().next()
                : SpringContextUtils.getBean(ISysDictFactory.class);
    }

}
