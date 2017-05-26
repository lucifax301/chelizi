alter table t_u_student add column career varchar(32) default null comment '职业';
alter table t_u_student add column cs varchar(32) default null comment '跟进客服';
alter table t_u_student add column loginCount int default 0 comment '登录次数';