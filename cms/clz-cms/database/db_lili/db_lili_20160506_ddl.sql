

USE db_lili;

DROP TABLE IF EXISTS t_s_schaccount;
CREATE TABLE `t_s_schaccount` (
  `schoolId` int(11) NOT NULL COMMENT '驾校ID',
  `money` int(11) DEFAULT '0' COMMENT '账户余额',
  `passwd` varchar(64) NOT NULL COMMENT '支付密码',
  `mobile` varchar(16) NOT NULL COMMENT '手机号',
  PRIMARY KEY (`schoolId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='驾校账户信息表';

DROP TABLE IF EXISTS t_s_school_extend;
CREATE TABLE `t_s_school_extend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `schoolId` int(11) NOT NULL COMMENT '驾校ID',
  `mer_name` varchar(64) NOT NULL COMMENT '商户名称',
  `regist_no` varchar(32) NOT NULL COMMENT '工商注册号',
  `org_code` varchar(20) NOT NULL COMMENT '组织机构代码',
  `tax_id` varchar(20) NOT NULL COMMENT '税务登记号',
  `public_account` varchar(20) NOT NULL COMMENT '对公账号',
  `bank_name` varchar(32) DEFAULT NULL COMMENT '开户银行',
  `sub_bank_name` varchar(64) DEFAULT NULL COMMENT '开户支行',
  `protocol_open` tinyint(4) DEFAULT '1' COMMENT '密码错误次数',
  `check_status` tinyint(4) DEFAULT '1' COMMENT '审核状态：1初始化，2审核通过，3审核不通过',
  `is_del` tinyint(4) DEFAULT '2' COMMENT '是否启用：1未启用，2，启用',
  `checker` varchar(32) DEFAULT NULL COMMENT '审核人',
  `check_time` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_change` tinyint(4) DEFAULT '1' COMMENT '是否有变更1无，2已提交申请，3已审核申请资料，4已驳回申请，5关闭',
  `change_remark` varchar(255) DEFAULT NULL COMMENT '变更审核备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='驾校账户信息表';

DROP TABLE IF EXISTS t_s_deposit;
CREATE TABLE `t_s_deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `waterId` varchar(32) NOT NULL COMMENT '流水ID',
  `school_type` tinyint(4) NOT NULL DEFAULT '3' COMMENT '用户类型：3-驾校',
  `school_id` bigint(20) NOT NULL COMMENT '驾校id',
  `money` int(11) NOT NULL COMMENT '提现金额（单位：分）',
  `apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请提现时间',
  `type` varchar(16) NOT NULL DEFAULT 'bank' COMMENT '提现渠道: bank银行卡 wexin:微信 zhifubao:支付宝 qqwallet:QQ钱包',
  `order_id` varchar(32) NOT NULL COMMENT '关联money表Orderid',
  `bank_card` varchar(32) NOT NULL COMMENT '银行卡号',
  `bank_name` varchar(32) DEFAULT NULL COMMENT '开户行名称',
  `card_name` varchar(64) NOT NULL COMMENT '持卡人姓名',
  `checker` varchar(32) DEFAULT NULL COMMENT '审批人',
  `check_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态,0-审核中，1-提现成功，2-提现失败，3-财务确认,4银行处理中',
  `check_time` timestamp NULL DEFAULT NULL COMMENT '审批时间',
  `check_remark` varchar(128) DEFAULT NULL COMMENT '提现驳回原因',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `transfer` varchar(32) DEFAULT NULL COMMENT '出纳操作人',
  `transferTime` timestamp NULL DEFAULT NULL COMMENT ' 出纳转账时间',
  `bankResTime` timestamp NULL DEFAULT NULL COMMENT '银行反馈时间',
  `real_money` int(11) DEFAULT NULL COMMENT '银行实际扣款',
  `prov` varchar(40) DEFAULT NULL COMMENT '开户省份',
  `city` varchar(80) DEFAULT NULL COMMENT '开户城市',
  `response_code` varchar(10) DEFAULT NULL COMMENT '银联返回应答码',
  `merDate` varchar(8) DEFAULT NULL COMMENT ' 原始商户日期',
  `merSeqId` varchar(16) DEFAULT NULL COMMENT '原始流水号',
  `stat` varchar(8) DEFAULT NULL COMMENT '交易状态码，定长1位',
  `feeAmt` int(12) DEFAULT NULL COMMENT '手续费（单位分），变长12位',
  `response_remark` varchar(255) DEFAULT NULL COMMENT '应答码说明',
  `stat_remark` varchar(255) DEFAULT NULL COMMENT '状态码说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='驾校提现明细表';

