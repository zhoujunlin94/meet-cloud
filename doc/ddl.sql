CREATE DATABASE IF NOT EXISTS `base` CHARACTER SET 'utf8mb4';
USE `base`;

CREATE TABLE `cache_config`
(
    `id`         int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `key`        varchar(255)     NOT NULL DEFAULT '' COMMENT '配置key',
    `value`      varchar(2048)    NOT NULL DEFAULT '' COMMENT '配置值',
    `desc`       varchar(1024)    NOT NULL DEFAULT '' COMMENT '配置描述',
    `is_delete`  tinyint(1)       NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位',
    `created_by` int(11)          NOT NULL DEFAULT '0' COMMENT '创建人',
    `created_at` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` int(11)          NOT NULL DEFAULT '0' COMMENT '更新人',
    `updated_at` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_key` (`key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='系统业务配置缓存表';

