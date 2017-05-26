CREATE TABLE t_jx_progress (
  name varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  phoneNum varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  idNumber varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  step varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  stepId int(11) DEFAULT NULL,
  state varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  stateId int(11) DEFAULT NULL,
  schoolId int(11) DEFAULT NULL,
  cityId int(11) DEFAULT NULL,
  classId varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  ctime datetime DEFAULT NULL,
  datatime datetime DEFAULT NULL,
  applyexam int(11) DEFAULT NULL,
  applystate int(11) DEFAULT NULL,
  student_id bigint(20) DEFAULT NULL,
  step_id int(11) DEFAULT NULL,
  step_state int(11) DEFAULT NULL,
  type tinyint(4) DEFAULT NULL COMMENT '1办证2批复',
  cpid int(11) DEFAULT NULL,
  lastUpdate datetime DEFAULT NULL,
  place varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  school varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  driveType varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  KEY idNumber (idNumber),
  KEY phoneNum (phoneNum)
);

truncate t_jx_student;
create index idNumber on t_jx_student(idNumber);
create index phoneNum on t_jx_student(phoneNum);
alter table t_p_car add column empId int(11) null comment '原系统教练ID';
alter table t_jx_student add column applyexam int(11) null;
alter table t_jx_student add column applystate int(11) null;
alter table t_jx_student add column type tinyint(4) null comment '1驾校2喱喱';
alter table t_jx_student add column student_id bigint(20) null;
alter table t_jx_student add column drType int(11) null after applyCarType;

alter table t_jx_student change homtown hometown varchar(128) null;