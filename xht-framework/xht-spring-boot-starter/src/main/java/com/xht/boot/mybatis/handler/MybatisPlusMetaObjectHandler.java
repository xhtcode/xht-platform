package com.xht.boot.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xht.framework.mybatis.enums.DelFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

import static com.xht.framework.security.constant.SecurityConstant.DEFAULT_ANONYMITY_USERNAME;

/**
 * MybatisPlus 自动填充配置
 *
 * @author xht
 **/
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("MybatisPlusMetaObjectHandler start insert fill....");
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "createBy", () -> DEFAULT_ANONYMITY_USERNAME, String.class);
        this.strictInsertFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateBy", () -> DEFAULT_ANONYMITY_USERNAME, String.class);
        // 删除标记自动填充
        this.strictInsertFill(metaObject, "delFlag", () -> DelFlagEnum.NORMAL, DelFlagEnum.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("start update fill....");
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updateBy", () -> DEFAULT_ANONYMITY_USERNAME, String.class);
    }

}
