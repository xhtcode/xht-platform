package com.xht.framework.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xht.framework.mybatis.enums.DelFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
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
public class MybatisPlusSecurityMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("MybatisPlusSecurityMetaObjectHandler start insert fill....");
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "createBy", this::getUserName, String.class);
        this.strictInsertFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateBy", this::getUserName, String.class);
        // 删除标记自动填充
        this.strictInsertFill(metaObject, "delFlag", () -> DelFlagEnum.NORMAL, DelFlagEnum.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("start update fill....");
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updateBy", this::getUserName, String.class);
    }

    /**
     * 获取当前登录用户名
     *
     * @return 当前登录用户名
     */
    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Optional.ofNullable(authentication).isPresent()) {
            return authentication.getName();
        }
        return null;
    }

}
