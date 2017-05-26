set @dbname=(select database());
select @dbname;

create table if not exists t_student_statistic (
	ssid int not null unique auto_increment primary key COMMENT '主键',
	student_id bigint not null COMMENT '学生id',
	ctid int not null COMMENT '评价标签id',
	total int not null default 0 COMMENT '该标签评价总次数',
	score int not null default 0 COMMENT '该标签总得分'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '学生得分统计表';

	alter table t_order add column allowance int not null default 0 COMMENT '教练补贴';
	alter table t_order add column price_total int COMMENT '总价价钱';
	alter table t_order add column pay_total int COMMENT '实际支付金额';
	alter table t_order add column pay_time datetime COMMENT '支付时间';
	alter table t_coach_class add column lge double COMMENT '训练场经度';
	alter table t_coach_class add column lae double COMMENT '训练场纬度';
	alter table t_coach_class add column rid int COMMENT '排班取消原因id,没有可不填写';
	alter table t_coach_class add column reason varchar(254) COMMENT '排班取消原因';
	
	
	create table if not exists t_skill_relation (
	  sid int not null unique auto_increment primary key COMMENT '主键',
		ctid int not null COMMENT '技能标签id',
		city_id int COMMENT '该技能需求的城市',
		course_id int COMMENT '该技能的课程', 
		subject_id int COMMENT '该技能的科目',
		isdel tinyint not null default 0 COMMENT '删除状态：0代表未删除，1代表已删除',
		cuid int comment '创建人id',
		muid int comment '更新人id',
		ctime datetime COMMENT '创建时间',
		mtime timestamp COMMENT '更新时间'
	)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '各课程技能点关系表'; 
	     
drop table if exists t_p_config;
CREATE TABLE `t_p_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT 'key',
  `value` varchar(255) DEFAULT NULL COMMENT '值',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `type` int(11) DEFAULT '0' COMMENT '0-所有用户；1-教练；2-学员',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余字段',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8;


