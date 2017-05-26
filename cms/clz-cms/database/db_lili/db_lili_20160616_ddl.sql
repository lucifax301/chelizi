

USE db_lili;

alter table t_s_school_extend add COLUMN sign tinyint(4) DEFAULT '1' COMMENT '标识：1-开通钱包，2-变更资料';


alter table t_s_school add COLUMN address varchar(254) DEFAULT NULL COMMENT '驾校地址';
alter table t_s_school add COLUMN phoneNum varchar(16) DEFAULT NULL COMMENT '客服电话';