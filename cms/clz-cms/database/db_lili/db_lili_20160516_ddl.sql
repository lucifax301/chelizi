

USE db_lili;

alter table t_s_school_extend add COLUMN sign tinyint(4) DEFAULT '1' COMMENT '标识：1-开通钱包，2-变更资料';

