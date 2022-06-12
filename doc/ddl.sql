CREATE DATABASE IF NOT EXISTS `base` CHARACTER SET 'utf8mb4';
USE `base`;
CREATE TABLE IF NOT EXISTS `cache_cfg`
(
    `key`   varchar(255) NOT NULL COMMENT '主键，配置key',
    `value` text         NOT NULL DEFAULT '' COMMENT '配置值',
    `desc`  varchar(1024)         DEFAULT NULL COMMENT '配置描述',
    PRIMARY KEY (`key`)
) ENGINE = InnoDB;

