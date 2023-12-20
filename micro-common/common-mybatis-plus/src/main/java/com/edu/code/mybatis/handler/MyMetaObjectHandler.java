package com.edu.code.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增填充创建时间
     * @param metaObject
     */

    @Override
    public void insertFill(MetaObject metaObject) {
       this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(),LocalDateTime.class);
       this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(),LocalDateTime.class);
    }

    /**
     * 更新填充时间
     * @param metaObject
     */

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
    }
}
