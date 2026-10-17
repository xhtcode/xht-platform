package com.xht.platform.dict.validation;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.utils.spring.SpringContextUtils;
import com.xht.platform.common.dict.domain.DictVO;
import com.xht.platform.common.dict.factory.ISysDictFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 描述：系统字典校验测试
 *
 * @author xht
 **/
class DictValidatorTest {

    /**
     * 借字段上的注解取得 {@link Dict} 实例，供 {@link DictValidator#initialize(Dict)} 使用
     */
    @Dict("user_status")
    private String status;

    private DefaultListableBeanFactory beanFactory;

    @BeforeEach
    void setUp() {
        beanFactory = new DefaultListableBeanFactory();
        // SpringContextUtils.beanFactory 为 private static，测试期注入独立容器，避免启动 Spring 上下文
        ReflectionTestUtils.setField(SpringContextUtils.class, "beanFactory", beanFactory);
    }

    @AfterEach
    void tearDown() {
        ReflectionTestUtils.setField(SpringContextUtils.class, "beanFactory", null);
    }

    @Test
    void shouldPassWhenValueIsEmpty() throws NoSuchFieldException {
        // 未注册任何字典门面，空值也应直接放行，说明空值分支不触发 bean 查找
        DictValidator validator = newValidator();
        assertTrue(validator.isValid(null, null));
        assertTrue(validator.isValid("", null));
        assertTrue(validator.isValid("   ", null));
    }

    @Test
    void shouldPassOnlyWhenValueEnabled() throws NoSuchFieldException {
        beanFactory.registerSingleton("sysDictFactory",
                (ISysDictFactory) dictCode -> List.of(dictVO("0", Boolean.FALSE), dictVO("1", Boolean.TRUE)));
        DictValidator validator = newValidator();
        assertTrue(validator.isValid("0", null), "未禁用的字典项应通过校验");
        assertFalse(validator.isValid("1", null), "已禁用的字典项不应通过校验");
        assertFalse(validator.isValid("2", null), "字典中不存在的项不应通过校验");
    }

    @Test
    void shouldTreatNullDisabledAsEnabled() throws NoSuchFieldException {
        beanFactory.registerSingleton("sysDictFactory",
                (ISysDictFactory) dictCode -> List.of(dictVO("0", null)));
        assertTrue(newValidator().isValid("0", null), "disabled 为 null 时应视为未禁用，且不能抛 NPE");
    }

    @Test
    void shouldFailFastWhenFactoryAbsent() throws NoSuchFieldException {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> newValidator().isValid("0", null));
        assertTrue(exception.getMessage().contains("xht-platform-client-dict"),
                "异常提示必须指明需要引入的依赖");
    }

    private DictValidator newValidator() throws NoSuchFieldException {
        Dict dict = DictValidatorTest.class.getDeclaredField("status").getAnnotation(Dict.class);
        DictValidator validator = new DictValidator();
        validator.initialize(dict);
        return validator;
    }

    private static DictVO dictVO(String value, Boolean disabled) {
        DictVO dictVO = new DictVO();
        dictVO.setValue(value);
        dictVO.setDisabled(disabled);
        return dictVO;
    }

}
