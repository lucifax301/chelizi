

USE db_lili_cms;

ALTER TABLE `t_log_common` MODIFY COLUMN `menu_name` VARCHAR (128) DEFAULT NULL COMMENT '操作菜单名称';

ALTER TABLE `t_log_common` MODIFY COLUMN `url` VARCHAR (128) DEFAULT NULL COMMENT 'url具体到action';

ALTER TABLE `t_log_common` MODIFY COLUMN `ip` VARCHAR (32) DEFAULT NULL COMMENT '操作所属IP地址';

ALTER TABLE `t_log_common` MODIFY COLUMN `table_id` INT (11) DEFAULT NULL COMMENT '关联表ID';

ALTER TABLE `t_log_common` MODIFY COLUMN `relate_table` VARCHAR (50) DEFAULT NULL COMMENT '操作记录表';

