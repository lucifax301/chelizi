alter table t_order add column operate_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
alter table t_student_class add column direct smallint default 0 comment '1 定向预约订单';
alter table t_student_class add column data_version varchar(32) default null;
