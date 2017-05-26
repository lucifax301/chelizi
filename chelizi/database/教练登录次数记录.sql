alter table t_u_coach add column lastCalculateDate datetime COMMENT '统计计算时间';
alter table t_u_coach add column loginCount int default 0 COMMENT '登录次数';
alter table t_u_coach add column inCount int default 0 COMMENT '收车次数';
alter table t_u_coach add column outCount int default 0 COMMENT '出车次数';
alter table t_u_coach add column listenTime bigint default 0 COMMENT '听单时间';
alter table t_u_coach add column classCount int default 0 COMMENT '排班次数';
alter table t_u_coach add column classTime bigint default 0 COMMENT '排班时长';

alter table t_u_coach add column teachTime bigint default 0 COMMENT '带教时长';
alter table t_u_coach add column workTime bigint default 0 COMMENT '工作时长';

alter table t_u_coach add column onlineTime bigint default 0 COMMENT '在线时长';
alter table t_u_coach add column lastOnlineDate datetime COMMENT '最后在线时间';
