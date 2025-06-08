package com.xht.framework.mybatis.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xht.framework.mybatis.enums.DelFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * MybatisPlus 自动填充配置
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
public class MybatisPlusSecurityMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("MybatisPlusSecurityMetaObjectHandler start insert fill....");
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "createBy", this::getUserName, String.class);
        this.strictInsertFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateBy", this::getUserName, String.class);
        // 删除标记自动填充
        this.strictInsertFill(metaObject, "isDel", () -> DelFlagEnum.NORMAL, DelFlagEnum.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill....");
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updateBy", this::getUserName, String.class);
    }


    /**
     * 审计字段自动填充
     *
     * @return {@link MetaObjectHandler}
     */
    @Bean
    public MybatisPlusSecurityMetaObjectHandler mybatisPlusMetaObjectHandler() {
        return new MybatisPlusSecurityMetaObjectHandler();
    }

    /**
     * 获取 spring security 当前的用户名
     *
     * @return 当前用户名
     */
    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 匿名接口直接返回
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "anonymity";
        }
        if (Optional.ofNullable(authentication).isPresent()) {
            return authentication.getName();
        }
        return null;
    }
}
