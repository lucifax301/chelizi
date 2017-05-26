drop table if exists t_balance_template;
create table t_balance_template (
bid int not null unique auto_increment primary key COMMENT '结算模板表主键',
bname varchar(20) COMMENT '该结算条目名称',
mtype int not null COMMENT '结算主类型：101教练充值，102教练提现；201学员第三方支付课时费,202学员余额支付课时费,203学员第三方支付报名费, 204学员余额支付报名费,205学员充值，206学员提现；301发券，302客服注销券,303客服挂起超时订单，304退课时费给学员，305报名费结算给驾校，306驾校罚款，306发奖金给教练;401驾校提现;501学员支付挂起，502券过期，503学员充值送',
stype int DEFAULT '0' COMMENT '结算子类型：为空表示主类型不再分类。从扩展性上考虑：目前202学员支付报名费采用城市作为子类型',
ptype tinyint COMMENT '支付方式不同导致结算方式不同：1余额支付，2第三方支付，为空表示无影响',
otype tinyint NOT NULL COMMENT '操作类型，为了匹配老业务，该字段为t_u_moneybiao中operateType的来源。', 
coach_type tinyint not null default 1 COMMENT '教练分配类型，请尽量优先比例分配：1代表比例分配，为百分比2位整数部分；2代表金额分配，金额的单位是分。',
coach_in varchar(10) not null default '0' COMMENT '教练收入',
coach_exp varchar(10) not null default '0' COMMENT '教练支出',
coach_per varchar(10) not null default '0' COMMENT '教练业绩',
coach_in_bal varchar(10) not null default 100 COMMENT '教练收入是否计入余额：计入比例，最大100，最小-100',
coach_exp_bal varchar(10) not null default -100 COMMENT '教练支出是否计入余额：计入比例，最大100，最小-100',
coach_per_bal varchar(10) not null default 0 COMMENT '教练业绩是否计入余额：计入比例，最大100，最小-100',
coach_in_msg varchar(256) COMMENT '教练收入文字描述，可能含变量',
coach_exp_msg varchar(256) COMMENT '教练支出文字描述，可能含变量',
coach_per_msg varchar(256) COMMENT '教练业绩文字描述，可能含变量',
stu_type tinyint not null default 1 COMMENT '学员分配类型，请尽量优先比例分配：1代表比例分配，为百分比2位整数部分，2代表金额分配，金额的单位是分。',
stu_in varchar(10) not null default '0' COMMENT '学员收入',
stu_exp varchar(10) not null default '0' COMMENT '学员支出',
stu_cou varchar(10) not null default '0' COMMENT '学员优惠',
stu_in_bal varchar(10) not null default 100 COMMENT '学员收入是否计入余额：计入比例，最大100，最小-100',
stu_exp_bal varchar(10) not null default -100 COMMENT '学员支出是否计入余额：计入比例，最大100，最小-100',
stu_cou_bal varchar(10) not null default 0 COMMENT '学员优惠是否计入余额：计入比例，最大100，最小-100',
stu_in_msg varchar(256) COMMENT '学员收入文字描述，可能含变量',
stu_exp_msg varchar(256) COMMENT '学员支出文字描述，可能含变量',
stu_cou_msg varchar(256) COMMENT '学员优惠文字描述，可能含变量',
lili_type tinyint not null default 1 COMMENT '喱喱分配类型，请尽量优先比例分配：1代表比例分配，为百分比2位整数部分，2代表金额分配，金额的单位是分。',
lili_in varchar(10) not null default '0' COMMENT '喱喱收入',
lili_exp varchar(10) not null default '0' COMMENT '喱喱支出',
lili_in_bal varchar(10) not null default 100 COMMENT '喱喱收入是否计入余额：计入比例，最大100，最小-100',
lili_exp_bal varchar(10) not null default -100 COMMENT '喱喱支出是否计入余额：计入比例，最大100，最小-100',
lili_in_msg varchar(256) COMMENT '喱喱收入文字描述，可能含变量',
lili_exp_msg varchar(256) COMMENT '喱喱支出文字描述，可能含变量',
sch_type tinyint not null default 1 COMMENT '驾校分配类型，请尽量优先比例分配：1代表比例分配，为百分比2位整数部分，2代表金额分配，金额的单位是分。',
sch_in varchar(10) not null default '0' COMMENT '驾校收入',
sch_exp varchar(10) not null default '0' COMMENT '驾校支出',
sch_in_bal varchar(10) not null default 100 COMMENT '驾校收入是否计入余额：计入比例，最大100，最小-100',
sch_exp_bal varchar(10) not null default -100 COMMENT '驾校支出是否计入余额：计入比例，最大100，最小-100',
sch_in_msg varchar(256) COMMENT '驾校收入文字描述，可能含变量',
sch_exp_msg varchar(256) COMMENT '驾校支出文字描述，可能含变量',
verify tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核状态:0代表未审核，1代表已审核,2审核拒绝',
isdel tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
cuid int(11) DEFAULT NULL COMMENT '创建人id',
muid int(11) DEFAULT NULL COMMENT '更新人id',
ctime datetime DEFAULT NULL COMMENT '创建时间',
mtime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) engine="INNODB" default charset="UTF8" COMMENT '收支结算模版表，用于所有结算的比例分配';





























