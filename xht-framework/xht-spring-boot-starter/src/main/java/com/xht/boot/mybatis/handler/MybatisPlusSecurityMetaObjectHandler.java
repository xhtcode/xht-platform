package com.xht.boot.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.enums.DelFlagEnum;
import com.xht.framework.oauth2.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MybatisPlus 自动填充配置
 *
 * @author xht
 **/
@Slf4j
public class MybatisPlusSecurityMetaObjectHandler implements MetaObjectHandler {

    private static final String DEFAULT_USERNAME = "anonymity";

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("MybatisPlusSecurityMetaObjectHandler start insert fill....");
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
        log.info("start update fill....");
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updateBy", this::getUserName, String.class);
    }

    private String getUserName() {
        return StringUtils.emptyToDefault(SecurityUtils.getUserName(), DEFAULT_USERNAME);
    }

}
