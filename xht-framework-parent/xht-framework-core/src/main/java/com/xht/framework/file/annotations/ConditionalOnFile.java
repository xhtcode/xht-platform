package com.xht.framework.file.annotations;

import com.xht.framework.file.configurers.FileStorageCondition;
import com.xht.framework.file.enums.StorageType;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 描述： 文件存在条件注解
 *
 * @author xht
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(FileStorageCondition.class)
public @interface ConditionalOnFile {

    /**
     * 存储类型
     */
    StorageType value();
}
