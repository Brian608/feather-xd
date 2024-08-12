package org.feather.xd.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.config
 * @className: MybatisPlusConfig
 * @author: feather
 * @description:
 * @since: 2024-08-11 18:08
 * @version: 1.0
 */
@Configuration
@ConditionalOnClass(MetaObjectHandler.class)
public class MybatisPlusConfig {
    /**
     * 通用字段自动填充配置
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createdTime", Date.class, new Date());
                this.strictInsertFill(metaObject, "updatedTime", Date.class, new Date());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updatedTime", Date.class, new Date());
            }
        };
    }
}
