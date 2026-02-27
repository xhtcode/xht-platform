package com.xht.boot.autoconfigure;

import com.xht.boot.security.context.SecurityUserContextServiceImpl;
import com.xht.framework.core.context.UserContextService;
import com.xht.framework.mybatis.mapper.common.UtilsMapper;
import com.xht.framework.oauth2.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * xht-boot 自动配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
public class XhtBootAutoConfiguration {


}
