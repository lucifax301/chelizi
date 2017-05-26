USE db_lili;

alter table t_recharge_record add COLUMN utype int(2) DEFAULT '1' COMMENT '用户类型 1学员 2教练';
