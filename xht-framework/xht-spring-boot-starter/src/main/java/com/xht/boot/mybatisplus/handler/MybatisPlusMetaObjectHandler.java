package com.xht.boot.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xht.framework.mybatis.enums.DelFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MybatisPlus 自动填充配置
 *
 * @author xht
 **/
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    private static final String DEFAULT_USERNAME = "anonymity";


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("MybatisPlusMetaObjectHandler start insert fill....");
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "createBy", () -> DEFAULT_USERNAME, String.class);
        this.strictInsertFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateBy", () -> DEFAULT_USERNAME, String.class);
        // 删除标记自动填充
        this.strictInsertFill(metaObject, "delFlag", () -> DelFlagEnum.NORMAL, DelFlagEnum.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill....");
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updateBy", () -> DEFAULT_USERNAME, String.class);
    }

}
