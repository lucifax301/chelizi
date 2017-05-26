drop table if exists t_vip_company;
create table  t_vip_company (
	coid int not null primary key auto_increment COMMENT '主键',
	company varchar(256) NOT NULL COMMENT '公司名称',
	vtype tinyint NOT NULL default 1 COMMENT '客户类型：1大客户，2散户',
	shorter varchar(50) COMMENT '公司简称',
	province_id int COMMENT '省id',
	city_id int COMMENT '市id',
	province varchar(50) COMMENT '省',
	city varchar(50) COMMENT '市',
	manger  varchar(50) NOT NULL COMMENT '公司联系人',
	mobile varchar(20) NOT NULL COMMENT '联系电话',
	phone  varchar(20) COMMENT '联系座机',
	email varchar(256) COMMENT '联系邮箱',
	rcid  varchar(256) COMMENT '优惠套餐id，可选多个，以逗号隔开，该公司下的员工只能选择公司套餐之一',
	remark varchar(256) COMMENT '客户备注',
	active tinyint NOT NULL default 1 COMMENT '是否激活：0未激活，1已经激活，',
	agreement text COMMENT '大客户协议',
	vstate tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态：0待审核，1审核通过,2审核拒绝',
	reason varchar(256) COMMENT '拒绝理由',
  isdel tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  cuid bigint(20) DEFAULT NULL COMMENT '创建人id',
  muid bigint(20) DEFAULT NULL COMMENT '更新人id',
  ctime datetime DEFAULT NULL COMMENT '创建时间',
  mtime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB default charset=utf8 COMMENT '大客户公司名录';

drop table if exists t_vip_custom;
create  table   t_vip_custom (
student_id bigint primary key COMMENT '用户id,大客户必须首先成为用户',
mobile varchar(20) not null COMMENT '冗余：申请人手机',
cname varchar(20)  COMMENT '冗余：申请人姓名',
coid int  COMMENT '大客户公司主键',
cid varchar(50) COMMENT '工号',
rcid int COMMENT '充值送套餐id',
coupon varchar(256) COMMENT '审核通过赠送的优惠券，多张使用逗号分割。',
coupon_lack varchar(256) COMMENT '审核通过由于优惠券失效而缺少赠送的券，多张使用逗号分割。',
vstate tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态：0待审核，1审核通过,2审核拒绝,9该客户优惠套餐已过期',
	reason varchar(256) COMMENT '拒绝理由',
  isdel tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  cuid bigint(20) DEFAULT NULL COMMENT '创建人id',
  muid bigint(20) DEFAULT NULL COMMENT '更新人id',
  ctime datetime DEFAULT NULL COMMENT '创建时间',
  mtime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)  ENGINE=InnoDB default charset=utf8 COMMENT '大客户额外用户信息记录，必须审核通过才是真正的大客户';

drop table if exists t_recharge_record;
create  table   t_recharge_record (
	rrid int not null auto_increment primary key COMMENT '主键Id',
	student_id bigint not null COMMENT '用户id',
	name varchar(50) COMMENT '冗余：学员姓名',
	mobile varchar(20) COMMENT '冗余：手机',
	company varchar(256) COMMENT '冗余：大客户公司名称',
	vtype tinyint NOT NULL default 1 COMMENT '冗余：客户类型：1大客户，2散户',
	charge int not null COMMENT '本次充值金额,单位分',
	recharge int not null COMMENT '本次赠送金额,单位分',
	water_id varchar(50) COMMENT '交易单号',
	rcid int not null COMMENT '充值送方案id:如果手工通过cms调整，则该id为0',
	rcname varchar(256) COMMENT '冗余：充值送方案名称,如果手工通过cms调整，则该为调整理由',
	pay_way varchar(10) NOT NULL COMMENT '冗余：支付方式',
	pay_time datetime not null COMMENT '支付时间',
	get_time datetime COMMENT '赠送资金到账时间',
	vstate tinyint(4) NOT NULL DEFAULT '0' COMMENT '财务审核状态：0待审核，1审核通过,2审核拒绝',
	reason varchar(256) COMMENT '拒绝理由',
	isdel tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
	cuid bigint(20) DEFAULT NULL COMMENT '创建人id',
	muid bigint(20) DEFAULT NULL COMMENT '更新人id',
	ctime datetime DEFAULT NULL COMMENT '创建时间',
	mtime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB default charset=utf8 COMMENT '客户充送记录，其中大客户只有审核通过才有此充送记录，而不自动送的记录这里还需要审核';

drop table if exists t_recharge_plan;
create table  t_recharge_plan (
 rcid int NOT NULL unique primary key auto_increment COMMENT '充值送方案主键，请保持9位：YYmmddXXX',
 rcname varchar(50) NOT NULL COMMENT '方案中文名称',
 vtype tinyint NOT NULL default 1 COMMENT '客户类型：1大客户，2散户',
 tstart datetime  COMMENT '方案开始时间',
 tend datetime COMMENT '方案结束时间',
 active tinyint NOT NULL default 1 COMMENT '是否激活：0未激活，1已经激活，',
 enroll tinyint NOT NULL default 1 COMMENT '生效条件：0无条件，1已经报名',
 auto tinyint NOT NULL default 0 COMMENT '是否在充值时自动赠送：0不自动赠送，1自动赠送',
 max_times tinyint COMMENT '本方案最多赠送次数,如果为空表示没有限制',
 indepent tinyint not null default 1 COMMENT '是否独立使用：1独立使用，即用了本充送就不能再使用其他充送方案，2可以和其他充送混合使用',
 jpush varchar(256) COMMENT '审核通过推送消息',
 ems varchar(56) COMMENT '审核通过推送短信',
 rejpush varchar(256) COMMENT '充值赠送到账推送消息',
 reems varchar(56) COMMENT '充值赠送到账推送短信',
 coupon_template varchar(256) COMMENT '该方案审核通过后赠送的优惠券模版，多条以逗号分割',
 coupon_number varchar(256) COMMENT '该方案审核通过后赠送的优惠券数量，与模版完全一一对应，多条以逗号分割',
 vstate tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态：0待审核，1审核通过,2审核拒绝',
 reason varchar(256) COMMENT '拒绝理由',
 isdel tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
 cuid bigint(20) DEFAULT NULL COMMENT '创建人id',
 muid bigint(20) DEFAULT NULL COMMENT '更新人id',
 ctime datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB default charset=utf8 COMMENT '充值送方案表，目前主要含大客户充值送方案，后续会增加普通客户方案';

drop table if exists t_recharge_gear;
create table  t_recharge_gear (
	rgid  int not null primary key auto_increment COMMENT '档位主键',
	rcid int NOT NULL COMMENT  '充值送方案主键',
	pmin int NOT NULL  COMMENT '最低金额，单位分。如果没有则无限制',
	pmax int NOT NULL  COMMENT '最高金额，单位分。如果没有则无限制',
	percent int  COMMENT '赠送比例,为百分比的两位整数部分',
	money int COMMENT '赠送金额，单位分',
	vstate tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态：0待审核，1审核通过,2审核拒绝',
  isdel tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  cuid bigint(20) DEFAULT NULL COMMENT '创建人id',
  muid bigint(20) DEFAULT NULL COMMENT '更新人id',
  ctime datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB default charset=utf8 COMMENT '充值送档位表,属于方案的最重要部分';


-- 添加大客户优惠方案和超级优惠方案
insert  into `t_recharge_plan`(`rcid`,`rcname`,`vtype`,`tstart`,`tend`,`active`,`enroll`,`auto`,`max_times`,`indepent`,`jpush`,`ems`,`rejpush`,`reems`,`coupon_template`,`coupon_number`,`vstate`,`reason`,`isdel`,`cuid`,`muid`,`ctime`) values (160701001,'大客户充送优惠方案',1,NULL,NULL,1,1,0,NULL,1,'您所选的是常规优惠套餐，报名优惠券已经发放只 <font color=\'#F48D07\'>我的钱包 - 优惠券</font> 中，报名后才可享受充值送优惠',NULL,'亲爱的用户，您的返现金额已到账至喱喱学车个人账户，请查看【喱喱学车】','97747','fd1642581f95449692f215cd96683f82','1',1,NULL,0,1,1,'2016-07-05 15:20:19');
insert  into `t_recharge_plan`(`rcid`,`rcname`,`vtype`,`tstart`,`tend`,`active`,`enroll`,`auto`,`max_times`,`indepent`,`jpush`,`ems`,`rejpush`,`reems`,`coupon_template`,`coupon_number`,`vstate`,`reason`,`isdel`,`cuid`,`muid`,`ctime`) values (160701002,'大客户充送限时超级优惠方案',1,NULL,'2016-06-30 23:59:59',1,1,0,NULL,1,'您所选的是限时优惠套餐，充值截至日期为2016年6月30日，赶快去充值吧',NULL,'亲爱的用户，您的返现金额已到账至喱喱学车个人账户，请查看【喱喱学车】','97747',NULL,NULL,1,NULL,0,1,1,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (1,160701001,50000,50000,10,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (2,160701001,100000,100000,15,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (3,160701001,150000,150000,20,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (4,160701001,200000,200000,25,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (5,160701001,250000,250000,30,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (6,160701001,300000,300000,40,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (7,160701002,100000,100000,50,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (8,160701002,150000,150000,50,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (9,160701002,200000,200000,50,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (10,160701002,250000,250000,50,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (11,160701002,300000,300000,100,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (12,160701002,350000,350000,100,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');
insert  into `t_recharge_gear`(`rgid`,`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) values (13,160701002,400000,400000,100,NULL,1,0,NULL,NULL,'2016-07-05 15:20:19');

-- 添加中国电信大客户公司和腾讯大客户公司
insert  into `t_vip_company`(`coid`,`company`,`vtype`,`shorter`,`province_id`,`city_id`,`province`,`city`,`manger`,`mobile`,`phone`,`email`,`rcid`,`remark`,`active`,`agreement`,`vstate`,`reason`,`isdel`,`cuid`,`muid`,`ctime`,`mtime`) values (1,'中国电信',1,NULL,NULL,100,'广东省','深圳市','Z经理','1300000000',NULL,NULL,NULL,NULL,1,'agreement',1,NULL,0,1,1,'2016-07-01 17:53:09','2016-07-01 17:53:22');
insert  into `t_vip_company`(`coid`,`company`,`vtype`,`shorter`,`province_id`,`city_id`,`province`,`city`,`manger`,`mobile`,`phone`,`email`,`rcid`,`remark`,`active`,`agreement`,`vstate`,`reason`,`isdel`,`cuid`,`muid`,`ctime`,`mtime`) values (2,'腾讯',1,NULL,NULL,100,'广东省','深圳市','T经理','13900000000',NULL,NULL,NULL,NULL,1,'agreement',1,NULL,0,1,1,'2016-07-01 17:53:10','2016-07-01 17:53:24');


-- 增加名称唯一索引 和 增补一些字段
ALTER TABLE t_recharge_plan ADD column agreement text COMMENT '服务协议' after coupon_number;
ALTER TABLE t_recharge_plan ADD column mtime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';
ALTER TABLE t_recharge_gear ADD column mtime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';
ALTER TABLE t_vip_custom ADD column rcname varchar(50) COMMENT '方案中文名称' after rcid;
ALTER TABLE t_vip_company MODIFY company VARCHAR(100) NOT NULL  UNIQUE COMMENT '公司名称';
ALTER TABLE t_recharge_plan ADD UNIQUE INDEX index_rcname (rcname);
ALTER TABLE t_recharge_record ADD UNIQUE INDEX index_waterid (water_id);


-- 增加报名生效城市,普惠方案开始
ALTER TABLE t_recharge_plan ADD column city_id varchar(256) COMMENT '该方案生效的城市，多个以逗号分割，为空表示不限制' after enroll;
ALTER TABLE t_recharge_plan ADD column city_name varchar(256) COMMENT '冗余：该方案生效的城市，多个以逗号分割' after city_id;
ALTER TABLE t_recharge_plan ADD column common tinyint not null default 0 COMMENT '普惠方案的当前优先版本：0一般，1优先，始终只有一个方案优先' after city_name;

ALTER TABLE t_recharge_plan ADD column need_verify tinyint not null default 1 COMMENT '成为大客户是否需要审核：1需要，0不需要' after common;
ALTER TABLE t_recharge_plan ADD column reg_use tinyint not null default 1 COMMENT '报名后是否还可以：0不可用,1可以用.' after need_verify;


delete from t_vip_company where coid=4;
insert  into `t_vip_company`(`coid`,`company`,`vtype`,`shorter`,`province_id`,`city_id`,`province`,`city`,`manger`,`mobile`,`phone`,`email`,`rcid`,`remark`,`active`,`agreement`,`vstate`,`reason`,`isdel`,`cuid`,`muid`,`ctime`,`mtime`) values (4,'喱喱学员普惠',2,NULL,NULL,100,'广东省','深圳市','喱喱经理','13900000000',NULL,NULL,NULL,NULL,1,'agreement',1,NULL,0,80,80,'2016-07-21 17:53:10','2016-07-21 17:53:24');

INSERT INTO `t_recharge_plan` (rcid,rcname,vtype,tstart,tend,active,enroll,city_id,city_name,common,need_verify,auto,max_times,indepent,jpush,ems,rejpush,reems,coupon_template,coupon_number,agreement,vstate,reason,isdel,cuid,muid,ctime,mtime) VALUES 
(160701003, '普惠充值送方案', 2, NULL, NULL, 1, 1, NULL, NULL, 1,0, 0, NULL, 1, '亲爱的学员，您享有充值送的优惠哦，快来充值学车吧。', NULL, NULL, '97747', NULL, NULL, NULL, 1, NULL, 0, 1, 1, '2016-7-21 12:52:07', '2016-7-21 14:29:16');

INSERT INTO `t_recharge_gear`(`rcid`,`pmin`,`pmax`,`percent`,`money`,`vstate`,`isdel`,`cuid`,`muid`,`ctime`) VALUES 
(160701003, 50000, 50000, NULL, 15000, 1, 0,  NULL, NULL, '2016-7-21 12:29:23'),
(160701003, 100000, 100000, NULL, 30000, 1, 0,  NULL, NULL, '2016-7-21 12:29:23'),
(160701003, 200000, 200000, NULL, 60000, 1, 0,  NULL, NULL, '2016-7-21 12:29:23'),
(160701003, 300000, 300000, NULL, 90000, 1, 0,  NULL, NULL, '2016-7-21 12:29:23');

-- 修改东莞广仁方案为大客户自动获得资格，不需要人工审核
update t_recharge_plan set need_verify=0 where rcid=160715001;