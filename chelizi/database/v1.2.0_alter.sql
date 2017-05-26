alter table t_stu_comment add column subject_id int COMMENT '冗余字段：被评价的科目';

UPDATE `t_stu_comment` a , `t_skill_relation` b, t_order c SET a.course_id=c.course_id, a.subject_Id=b.Subject_id WHERE a.ctid=b.ctid AND  a.order_id=c.order_Id AND a.course_id=b.course_id;

alter table t_u_student add column drLicence varchar(32) COMMENT '驾照编号';
alter table t_u_student add column drPhoto varchar(32) COMMENT '驾照图片地址';
alter table t_u_student add column drType int COMMENT '驾驶类型（1代表C1,2代表C2,3代表其它）';
alter table t_u_student add column agreement tinyint(4) default 0 COMMENT '是否同意了用户协议 0-未同意 1-已同意';
alter table t_u_student add column applyexam tinyint(4) default 0 COMMENT '车管所报名状态：0-未报名；1-已报名成功；2-报名审核中';
alter table t_u_student add column pwstate tinyint(4) default 0 COMMENT '是否设置了支付密码- 0-未设置；1-已设置';

alter table t_u_coach add column agreement tinyint(4) default 0 COMMENT '是否同意了用户协议 0-未同意 1-已同意';
alter table t_u_coach add column pwstate tinyint(4) default 0 COMMENT '是否设置了支付密码- 0-未设置；1-已设置';

alter table t_u_coachaccount add column passwd varchar(64) COMMENT '支付密码';
alter table t_u_stuaccount add column passwd varchar(64) COMMENT '支付密码';
alter table t_u_money add column status tinyint(4) DEFAULT NULL COMMENT '状态(1成功，2失败)';



CREATE TABLE `t_u_deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `waterId` varchar(32) NOT NULL COMMENT '流水ID',
  `user_type` tinyint(4) NOT NULL COMMENT '用户类型：1-教练，2-学员',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `money` int(11) NOT NULL COMMENT '提现金额（单位：分）',
  `apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请提现时间',
  `type` varchar(16) NOT NULL DEFAULT 'bank' COMMENT '提现渠道: bank银行卡 wexin:微信 zhifubao:支付宝 qqwallet:QQ钱包',
  `verify_id` int(11) NOT NULL COMMENT '关联银行卡绑定表ID',
  `order_id` varchar(32) NOT NULL COMMENT '关联money表Orderid',
  `bank_card` varchar(32) NOT NULL COMMENT '银行卡号',
  `bank_name` varchar(32) DEFAULT NULL COMMENT '开户行名称',
  `card_name` varchar(32) NOT NULL COMMENT '持卡人姓名',
  `checker` varchar(32) DEFAULT NULL COMMENT '审批人',
  `check_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态,0-待反馈，1-提现成功，2-交易失败',
  `check_time` timestamp NULL DEFAULT NULL COMMENT '审批时间',
  `check_remark` varchar(128) DEFAULT NULL COMMENT '提现驳回原因',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='提现明细表';


CREATE TABLE `t_u_bankcard_verify` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `user_type` tinyint(4) NOT NULL COMMENT '用户类型：1-教练，2-学员',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `bank_no` varchar(32) NOT NULL COMMENT '银行卡号',
  `bank_name` varchar(32) NOT NULL COMMENT '开户行',
  `bank_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '银行卡类型：0-储蓄卡，1-信用卡',
  `card_bin` int(3) DEFAULT NULL COMMENT '信用卡bin',
  `name` varchar(32) NOT NULL COMMENT '持卡人姓名',
  `mobile` varchar(16) NOT NULL COMMENT '银行绑定手机号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-待反馈，1-有效卡号，2-无效卡号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `checker` varchar(32) DEFAULT NULL COMMENT '财务审核人',
  `check_time` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_del` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态：0代表未删除，1代表已删除',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='银行卡实名绑定表';



alter table t_order add column city_id  int COMMENT '订单的城市id';
alter table t_u_student add column cityId  int COMMENT '学员所属城市id';
alter table t_s_school add column cityId  int COMMENT '驾校所在城市id';
alter table t_p_car add column cityId  int COMMENT '训练车所属城市id';
alter table t_p_car add column schoolId  int default 1 COMMENT '驾校id';


