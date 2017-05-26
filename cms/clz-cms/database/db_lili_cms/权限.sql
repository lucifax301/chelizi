CREATE TABLE `t_s_school_account_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `schoolName` varchar(64) NOT NULL COMMENT '学校名字',
  `shortName` varchar(32) DEFAULT NULL COMMENT '学校简称',
  `schoolAccount` varchar(128) NOT NULL COMMENT '帐号',
  `schoolPwd` varchar(64) NOT NULL COMMENT '密码',
  `province` varchar(20) DEFAULT NULL COMMENT '',
  `city` varchar(32) NOT NULL COMMENT '城市',
  `cityId` varchar(10) NOT NULL COMMENT '城市ID',
  `address` varchar(128) DEFAULT NULL COMMENT '详细地址',
  `applyer` varchar(32) DEFAULT NULL COMMENT '申请人',
  `phoneNum` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `status` smallint DEFAULT 0 COMMENT '申请状态 0 未审核 1 审核通过 2审核不通过',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='驾校账户申请表';

alter table t_c_permission add column ajax varchar(64) default null;
alter table t_c_permission add column menuOrder smallint(2) default 0;
alter table t_c_permission add column layoutOrder smallint(2) default 0;
alter table t_c_permission add column level smallint default 1;
alter table t_c_permission add column must smallint default 0;

alter table t_c_role add column creator varchar(30) default null;
alter table t_c_role add column updator varchar(30) default null;
alter table t_c_role add column createTime datetime default null;
alter table t_c_role add column updateTime datetime default null;

alter table t_c_user add column creator varchar(30) default null;
alter table t_c_user add column updator varchar(30) default null;
alter table t_c_user add column createTime datetime default null;
alter table t_c_user add column updateTime datetime default null;
create unique index account_idx on t_c_user(account);
update t_c_permission set level=-1;

INSERT INTO `t_c_role` VALUES (0,1,0,0,0,1,'车厘子',0,'车厘子总部,顶级部门',NULL,NULL,now(),NULL);
insert into t_c_role_permission select id,1 from t_c_permission;
INSERT INTO `t_c_user` VALUES (0,1,'admin','4ed1b0ed1f3090fbe052ab3e287911cbe514e3f3$499781','管理员',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
update `t_c_user` set enabled=0;
insert into t_c_user_role select  id,1 from t_c_user where schoolId=0;

update t_c_user set enabled=0 where enabled is null;

insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(110000,0,'数据统计','',3,1,1,1,1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120000,0,'资源管理','',3,1,1,2,2);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130000,0,'账目管理','',3,1,1,3,3);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(140000,0,'APP设置','',3,1,1,4,4);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder,must) values(150000,0,'用户管理','',3,1,1,5,5,1);


insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(110100,110000,'教务报表','datas/data-statistics.html',3,2,1,1,1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120100,120000,'订单','order.html',1,2,1,1,1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120200,120000,'报名订单','sign-up-orde.html',1,2,1,2,2);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120300,120000,'教练','coach.html',1,2,1,3,3);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120400,120000,'驾校学员','student-for-schol.html',1,2,1,4,-1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120500,120000,'学员管理','student.html',1,2,1,5,5);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120600,120000,'教练车','car.html',1,2,1,6,6);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120700,120000,'训练场','field.html',1,2,1,7,7);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120800,120000,'驾校','schools.html',1,2,1,8,8);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(120900,120000,'大客户','big-client.html',1,2,1,9,9);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121000,120000,'导入驾校资源','import-school.html','/school-data/data-batch',1,2,1,10,10);

insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130100,130000,'财务收支统计','accounts-balance-for-lili.html',1,2,1,1,1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130200,130000,'优惠券','coupon.html',1,2,1,2,2);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130300,130000,'券条件','coupon-condition.html',1,2,1,3,3);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130400,130000,'余额管理','bonus.html',1,2,1,4,4);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130500,130000,'变更余额','bonus-balance.html',1,2,1,5,-1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130600,130000,'提现','withdraw-cash.html',1,2,1,6,6);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130700,130000,'驾校提现','withdraw-school.html',1,2,1,7,-1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130800,130000,'个人充值','recharge.html',1,2,1,8,8);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(130900,130000,'优惠充值','recharge-discount.html',1,2,1,9,-1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(131000,130000,'银行卡','bank-cad.html',1,2,1,10,10);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(131100,130000,'喱喱钱包','lili-purse.html',1,2,1,11,11);


insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(140100,140000,'充值送方案','app-client-plan.html',1,2,1,1,1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(140200,140000,'通知','notice.html',1,2,1,2,2);

insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(150100,150000,'用户管理','user-list.html',1,2,1,1,1);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder) values(150200,150000,'角色管理','user-permission.html',1,2,1,2,2);
insert into t_c_permission(id,pid,name,url,type,level,enabled,layoutOrder,menuOrder,must) values(150300,150000,'个人设置','change-password.html',1,2,1,3,3,1);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(110101,110100,'数据统计','datas/data-statistics.html','/statistics-total/list',1,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(110102,110100,'订单统计','datas/data-ord-statistics.html','/statistics-total/order',1,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(110103,110100,'学员统计','datas/data-stu-statistics.html','/statistics-total/student',1,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(110104,110100,'教练统计','datas/data-coa-statistics.html','/statistics-total/coach',1,3,1,4,4);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120101,120100,'关闭订单','','/order/update-batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120102,120100,'查看订单','','/order/batch',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120103,120100,'查看订单详情','order-details.html','/order/view',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120104,120100,'订单挂起','','/order/hangUp',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120105,120100,'数据导出','','/order/export-excel',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120106,120100,'退款申请','refund.html','/order/queryRefund',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120107,120100,'退款成功','','/order/subRefund',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120108,120100,'退款成功','order-details.html','/logCommon/batch',2,3,1,8,8);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120109,120100,'订单调度','order-attemper.html','/order/schedule/orders',2,3,1,9,9);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120110,120100,'指派所选教练','order-attemper.html','/order/schedule/coach',2,3,1,10,10);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120111,120100,'查询订单列表','order-attemper.html','/order/schedule/orders',2,3,1,11,11);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120112,120100,'申请退款','','order/refund',2,3,1,12,12);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120201,120200,'查看报名订单','','/enrollOrder/query',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120202,120200,'报名费结算查询','sign-up-statement.html','/enrollOrder/balanceQuery',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120203,120200,'结款给驾校','','/enrollOrder/payment',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120204,120200,'查看报名订单详情','/enrollOrder/detail','/enrollOrder/detail',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120205,120200,'导出报名订单','sign-up-orde.html','/enrollOrder/export-excel',2,3,1,5,5);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120301,120300,'查看列表','','/coach/batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120302,120300,'新增教练','','/coach/add',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120303,120300,'编辑教练','','/coach/update',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120304,120300,'历史订单','','/order/batch',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120305,120300,'关闭订单','','/order/update-batch',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120306,120300,'带教学员','','/coach/student',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120307,120300,'教练车','','/coach/car',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120308,120300,'个人账户','','/coach/account',2,3,1,8,8);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120309,120300,'操作日志','','/coach/batch',2,3,1,9,9);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120310,120300,'数据导出','','/coach/export-excel',2,3,1,10,10);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120311,120300,'分配新学员','','/coach/new-stu',2,3,1,11,11);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120312,120300,'删除带教学员','','',2,3,1,12,12);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120313,120300,'绑定车辆','','/coach/new-car',2,3,1,13,13);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120314,120300,'删除车辆','','/coach/del-rel',2,3,1,14,14);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120315,120300,'封号','','/coach/lock-batch',2,3,1,15,15);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120316,120300,'教练','','/coach/view',2,3,1,16,16);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120317,120300,'分配新学员前查询学员','coach-details.html','/student/no-coach',2,3,1,17,17);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120318,120300,'分配教练车时查询教练车','coach-details.html','/car/coach',2,3,1,18,18);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120401,120400,'查看列表','','/student/jx-batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120402,120400,'新增学员','','/student/add',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120403,120400,'编辑学员','','/student/update',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120404,120400,'历史订单','','/order/batch',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120405,120400,'关闭订单','','/order/update-batch',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120406,120400,'我的教练','','/student/coach',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120407,120400,'个人账户','','/student/account',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120408,120400,'操作日志','','/logCommon/batch',2,3,1,8,8);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120409,120400,'分配教练','','/student/new-coach',2,3,1,9,9);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120410,120400,'删除教练','','/student/del-rel',2,3,1,10,10);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120411,120400,'更换教练','','/student/new-coach',2,3,1,11,11);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120412,120400,'数据导出','','/student/export-excel',2,3,1,12,12);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120413,120400,'置学员状态','','/student/reset-state',2,3,1,13,13);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120414,120400,'驾校学员信息','student-for-schol-details.html','/student/view',2,3,1,14,14);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120501,120500,'查看列表','','/student/lili-batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120502,120500,'编辑学员','','/student/update',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120503,120500,'资料已邮寄','','/student/reset-state',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120504,120500,'资料不全','','/student/pack-detail',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120505,120500,'资料齐全','','/student/pack-detail',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120506,120500,'表已寄出','','/student/reset-state',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120507,120500,'封号','','/student/lock-batch',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120508,120500,'历史订单','','/order/batch',2,3,1,8,8);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120509,120500,'关闭订单','','/order/batch',2,3,1,9,9);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120510,120500,'个人账户','','/order/account',2,3,1,10,10);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120511,120500,'操作日志','','/logCommon/batch',2,3,1,11,11);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120512,120500,'数据导出','','/student/export-excel',2,3,1,12,12);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120513,120500,'查看驾校分配情况','student-distion-school.html','/student/school-batch',2,3,1,13,13);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120514,120500,'分配驾校','student-distion-school.html','/student/allot',2,3,1,14,14);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120515,120500,'查看实名认证列表','student-identity-accreditation.html','/student/cer-batch',2,3,1,15,15);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120516,120500,'实名认证不通过','student-identity-accreditation.html','/student/update-auth',2,3,1,16,16);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120517,120500,'实名认证通过','student-identity-accreditation.html','/student/update-auth',2,3,1,17,17);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120518,120500,'实名认证吊销','student-identity-accreditation.html','/student/update-auth',2,3,1,18,18);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120519,120500,'查看驾照认证列表','student-identity-accreditation.html','/student/lin-batch',2,3,1,19,19);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120520,120500,'驾照认证不通过','student-identity-accreditation.html','/student/update-auth',2,3,1,20,20);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120521,120500,'驾照认证通过','student-identity-accreditation.html','/student/update-auth',2,3,1,21,21);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120522,120500,'驾照认证吊销','student-identity-accreditation.html','/student/update-auth',2,3,1,22,22);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120523,120500,'喱喱学员详情','student-details.html','/student/view',2,3,1,23,23);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120524,120500,'喱喱学员历史订单','student-details.html','/order/batch',2,3,1,24,24);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120525,120500,'喱喱学员个人帐户','student-details.html','/student/account',2,3,1,25,25);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120526,120500,'喱喱学员操作日志','student-details.html','/logCommon/batch',2,3,1,26,26);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120601,120600,'查看车辆列表','','/car/batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120602,120600,'新增车辆','','/car/add',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120603,120600,'编辑车辆','','/car/update',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120604,120600,'历史订单','','/order/batch',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120605,120600,'操作日志','','/logCommon/batch',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120606,120600,'数据导出','','/car/export-excel',2,3,1,6,6);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120607,120600,'数据导出','field-details.html','/car/view',2,3,1,7,7);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120701,120700,'查看训练场列表','','/field/batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120702,120700,'新增训练场','','/field/add',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120703,120700,'编辑训练场','','/field/update',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120704,120700,'数据导出','','/field/export-excel',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120705,120700,'训练场操作日志','field-details.html','/logCommon/batch',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120706,120700,'训练场信息','field-details.html','/field/view',2,3,1,6,6);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120801,120800,'查看驾校列表','','/school/querySchool',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120802,120800,'新增驾校','','/school/add',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120803,120800,'编辑驾校','','/school/update',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120804,120800,'数据导出','','/school/export-excel',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120805,120800,'驾校钱包管理','schools-purse.html','/school/queryBurse',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120806,120800,'审核通过','schools-purse.html','/school/checkPass',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120807,120800,'同意变更','schools-purse.html','/school/agreeChange',2,3,1,7,8);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120808,120800,'审核不过','schools-purse.html','/school/checkReject',2,3,1,8,8);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120809,120800,'拒绝变更','schools-purse.html','/school/refuseChange',2,3,1,9,9);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120810,120800,'驾校帐号审核','','/school/applyQuery',2,3,1,10,10);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120811,120800,'驾校帐号审核通过','','/school/applyAudit',2,3,1,10,10);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120812,120800,'驾校帐号审核不通过','','/school/applyUnAudit',2,3,1,10,10);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120813,120800,'驾校信息','schools-details.html','/school/queryAccount',2,3,1,11,11);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120814,120800,'驾校余额帐单','schools-details.html','/school/touchBalance',2,3,1,12,12);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120815,120800,'驾校收入明细','schools-details.html','/school/accountBalance',2,3,1,13,13);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120816,120800,'驾校费用明细','schools-details.html','/school/costDetail',2,3,1,14,14);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120901,120900,'大客户列表','','/vip/company-batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120902,120900,'新建大客户','','/vip/add-company',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120903,120900,'数据导出','big-client-details.html','/vip/export-excel',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120904,120900,'激活启用','','/vip/active-company',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120905,120900,'停止发放','','/vip/inactive-company',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120906,120900,'大客户详情','big-client-details.html','/vip/company-view,/vip/plan-batch,/vip/custom-batch',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120907,120900,'编辑大客户','','/vip/modify-company',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120908,120900,'学员列表','big-client-details.html','/vip/custom-batch',2,3,1,8,8);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120909,120900,'添加大客户学员','big-client-details.html','/vip/add-custom,/student/phone',2,3,1,9,9);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120910,120900,'新增学员','big-client-details.html','/vip/new-student',2,3,1,10,10);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120911,120900,'审核通过','big-client-details.html','/vip/pass-custom',2,3,1,11,11);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120912,120900,'审核拒绝','big-client-details.html','/vip/refuse-custom',2,3,1,12,12);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120913,120900,'学员导入','big-client-details.html','/vip/upload',2,3,1,13,13);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120914,120900,'下载模版','big-client-details.html','/vip/download',2,3,1,14,14);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(120915,120900,'数据导出','big-client-details.html','vip/export-company',2,3,1,15,15);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121001,121000,'导入驾校资源','','/school-data/upload',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(121002,121000,'下载模版','','/school-data/downLoadTemplate',2,3,1,2,2);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130101,130100,'平台账户','','/par/batch',2,3,1,1,1);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130201,130200,'查看列表','','/coupon/coupon-batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130202,130200,'申请新券','applyNewCoupon.html','/coupon/add-coupon,/coupon/use-list,/coupon/eve-tip',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130203,130200,'编辑优惠券','edit-coupon.html','/coupon/coupon-view,/coupon/update-coupon,/coupon/eve-tip',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130204,130200,'审核通过','','/coupon/audit-coupon',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130205,130200,'审核不过','','/coupon/unaudit-coupon',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130206,130200,'激活启用','','/coupon/active-coupon',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130207,130200,'停止发放','','/coupon/cancle-coupon',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130208,130200,'追加发行','','/coupon/add-stock',2,3,1,8,8);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130209,130200,'指定学员发券','','/coupon/student-coupon',2,3,1,9,9);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130210,130200,'注销','','/coupon/cancle-stucoupon',2,3,1,10,10);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130211,130200,'查看优惠券详情','coupon-details.html','/coupon/coupon-view',2,3,1,11,11);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130212,130200,'查看优惠券下的学员列表','coupon-details.html','/coupon/stucoupon-batch',2,3,1,12,12);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130213,130200,'按手机号搜索查询','coupon-details.html','/student/phone',2,3,1,13,13);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130301,130300,'查看列表','','/coupon/cond-batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130302,130300,'新建条件','','/coupon/add-cond',2,3,1,2,2);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130401,130400,'查看奖金','','/bonus/query',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130402,130400,'下载模版','','/bonus/downLoadTemplate',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130403,130400,'导入新表','','/bonus/upload',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130404,130400,'确认提交','','/bonus/submit',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130405,130400,'删除','','/bonus/delete',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130406,130400,'发放奖金','','/bonus/grant',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130407,130400,'拒绝发放','','/bonus/finReject',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130408,130400,'查看资金详情','/bonus.html','/bonus/queryDetail',2,3,1,8,8);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130501,130500,'查看列表','','/vip/balance-batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130502,130500,'审核通过','','/vip/pass-balance',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130503,130500,'审核不过','','/vip/refuse-balance',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130504,130500,'新增变更余额','add-variation.html','/vip/add-record',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130505,130500,'数据导出','','/vip/export-balance',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130506,130500,'搜索学员教练','add-variation.html','/student/findStudentOrCoach',2,3,1,6,6);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130601,130600,'查看列表','withdraw-cash.html','/bankDeposit/query',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130602,130600,'财务确认','withdraw-cash.html','/bankDeposit/financePass',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130603,130600,'财务拒绝','withdraw-cash.html','/bankDeposit/reject',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130604,130600,'出纳转账','withdraw-cash.html','/bankDeposit/tellerTransfer',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130605,130600,'提现成功','withdraw-cash.html','/bankDeposit/pass',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130606,130600,'提现失败','withdraw-cash.html','/bankDeposit/reject',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130607,130600,'下载txt数据','withdraw-cash.html','/bankDeposit/downLoad',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130608,130600,'下载excel表','withdraw-cash.html','/bankDeposit/downLoadExcel',2,3,1,8,8);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130701,130700,'查看列表','withdraw-school.html','/schoolDeposit/query',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130702,130700,'财务确认','withdraw-school.html','/schoolDeposit/financePass',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130703,130700,'财务拒绝','withdraw-school.html','/schoolDeposit/reject',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130704,130700,'出纳转账','withdraw-school.html','/schoolDeposit/tellerTransfer',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130705,130700,'提现成功','withdraw-school.html','/schoolDeposit/pass',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130706,130700,'提现失败','withdraw-school.html','/schoolDeposit/reject',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130707,130700,'下载txt数据','withdraw-school.html','/schoolDeposit/downLoad',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130708,130700,'下载excel表','withdraw-school.html','/schoolDeposit/downLoadExcel',2,3,1,8,8);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130801,130800,'查看列表','','/payHistory/query',2,3,1,1,1);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130901,130900,'查看列表','','/vip/record-batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130902,130900,'审核通过','','/vip/pass-record',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(130903,130900,'审核不过','','/vip/refuse-record',2,3,1,3,3);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131001,131000,'查看列表','','/boundBankCard/query',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131002,131000,'有效','','/boundBankCard/pass',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131003,131000,'无效','','/boundBankCard/reject',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131004,131000,'下载txt文档','','/boundBankCard/downLoad',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131005,131000,'下载excel表','','/boundBankCard/downLoadExcel',2,3,1,5,5);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131101,131100,'月度盈亏统计','lili-purse.html','liliWallet/fundCount',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131102,131100,'余额帐户','lili-purse.html','liliWallet/balance',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131103,131100,'月度收入明细','lili-purse.html','liliWallet/accountBalance',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131104,131100,'月度费用明细','lili-purse.html','liliWallet/costDetail',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(131105,131100,'喱喱余额帐单明细','lili-purse.html','liliWallet/touchBalance',2,3,1,5,5);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140101,140100,'查看列表','','/vip/plan-batch',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140102,140100,'新建充送方案','','/vip/add-plan',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140103,140100,'激活启用','','/vip/active-plan',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140104,140100,'停止发放','','/vip/inactive-plan',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140105,140100,'充送详情','','/vip/recharge-plan,/vip/custom-batch',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140106,140100,'删除学员','','/vip/custom-delete',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140107,140100,'编辑充值送方案','app-client-plan-edit.html','/vip/edit-plan',2,3,1,7,7);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140201,140200,'通知','','/notice/getNotice',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140202,140200,'新增消息','','/notice/add',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(140203,140200,'新增消息','','/notice/update',2,3,1,3,3);


insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150101,150100,'查看列表','','/user/findUser,/user/findAvailUser',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150102,150100,'新建用户','','/user/add',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150103,150100,'编辑用户','','/user/update',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150104,150100,'恢复账号','','/user/active',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150105,150100,'离职停用','','/user/cancle',2,3,1,5,5);

insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150201,150200,'查看列表','','/privilege/listRole',2,3,1,1,1);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150202,150200,'新建角色','','/privilege/addRole',2,3,1,2,2);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150203,150200,'编辑角色','','/privilege/updateRole',2,3,1,3,3);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150204,150200,'激活启用','','/privilege/activeRole',2,3,1,4,4);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150205,150200,'停用角色','','/privilege/inactiveRole',2,3,1,5,5);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150206,150200,'成员管理','','/privilege/listRoleUser',2,3,1,6,6);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150207,150200,'添加成员','','/privilege/addRoleUser',2,3,1,7,7);
insert into t_c_permission(id,pid,name,url,ajax,type,level,enabled,layoutOrder,menuOrder) values(150208,150200,'删除成员','','/privilege/delRoleUser',2,3,1,8,8);