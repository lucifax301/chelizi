

USE db_lili_cms;

alter table t_log_common add column detail varchar(500) default null comment '修改字段详细记录json';


