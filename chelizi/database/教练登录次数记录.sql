alter table t_u_coach add column lastCalculateDate datetime COMMENT 'ͳ�Ƽ���ʱ��';
alter table t_u_coach add column loginCount int default 0 COMMENT '��¼����';
alter table t_u_coach add column inCount int default 0 COMMENT '�ճ�����';
alter table t_u_coach add column outCount int default 0 COMMENT '��������';
alter table t_u_coach add column listenTime bigint default 0 COMMENT '����ʱ��';
alter table t_u_coach add column classCount int default 0 COMMENT '�Ű����';
alter table t_u_coach add column classTime bigint default 0 COMMENT '�Ű�ʱ��';

alter table t_u_coach add column teachTime bigint default 0 COMMENT '����ʱ��';
alter table t_u_coach add column workTime bigint default 0 COMMENT '����ʱ��';

alter table t_u_coach add column onlineTime bigint default 0 COMMENT '����ʱ��';
alter table t_u_coach add column lastOnlineDate datetime COMMENT '�������ʱ��';
