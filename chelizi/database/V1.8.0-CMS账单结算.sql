-- money记录表添加字段,表示当前记录是收入还是支出
ALTER TABLE `db_lili`.`t_u_money`
ADD COLUMN `isEarning` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '是否为收入：0=支出，1=收入' AFTER `status`;

-- money记录表添加字段,表示当前记录是余额或否
ALTER TABLE `db_lili`.`t_u_money`
ADD COLUMN `isBalance` TINYINT NOT NULL DEFAULT 0 COMMENT '是否为余额：0不是，1是' AFTER `isEarning`;

-- 修改注释,添加操作类型的状态
ALTER TABLE `db_lili`.`t_u_money`
CHANGE COLUMN `operateType` `operateType` TINYINT(4) NOT NULL COMMENT '操作类型(0充值，1提现，2奖金，3补贴, 5课时费支付，7报名费支付，8课时费佣金，9报名费佣金，10报名费退款，11报名费佣金退款，12罚款，13报名费结算，14优惠券使用，15挂起订单，16支付挂起订单，17报名违约金，18充值送现金)' ;

DROP TABLE IF EXISTS `t_order_refund`;
CREATE TABLE `t_order_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(34) NOT NULL,
  `asker` varchar(45) DEFAULT NULL COMMENT '申请人',
  `operator` varchar(45) DEFAULT NULL COMMENT '操作人',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `refundMoney` int(11) NOT NULL DEFAULT '0' COMMENT '退款金额，单位为分',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '退款状态：0退款中，1已退款',
  `refundTime` timestamp NULL DEFAULT NULL COMMENT '退款时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单退款详情';

-- 弃用payee字段,增加schoolid字段
ALTER TABLE `db_lili`.`t_brokerage_enroll`
CHANGE COLUMN `payee` `payee` TINYINT(4) NULL COMMENT '字段弃用' ,
ADD COLUMN `schoolId` INT NOT NULL COMMENT '驾校Id' AFTER `regionId`;


-- 添加结款人字段
ALTER TABLE `db_lili`.`t_enroll_order`
ADD COLUMN `checkouter` VARCHAR(45) NULL COMMENT '结款人' AFTER `extra`;

-- 添加结款时间字段
ALTER TABLE `db_lili`.`t_enroll_order`
CHANGE COLUMN `checkouter` `checkouter` VARCHAR(45) NULL COMMENT '结款人' ,
ADD COLUMN `checkoutTime` TIMESTAMP NULL COMMENT '结款时间' AFTER `checkouter`;


-- 不需要的字段,可输入为null
ALTER TABLE `db_lili`.`t_brokerage_enroll`
CHANGE COLUMN `projectType` `projectType` TINYINT(4) NULL COMMENT '' ;

-- 订单添加结款状态
ALTER TABLE `db_lili`.`t_order` ADD COLUMN `checkout_state` TINYINT NOT NULL DEFAULT 0 COMMENT '结款状态：0=未结款，1=结款' AFTER `school_Id`;
-- 报名订单支付字段更改注释
ALTER TABLE `db_lili`.`t_enroll_order` CHANGE COLUMN `pay_state` `pay_state` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '支付状态：0未开始，1已提交，100支付成功，101支付失败，103退款中，104退款失败，105已退款' ;
-- 报名订单添加结款状态
ALTER TABLE `db_lili`.`t_enroll_order` ADD COLUMN `checkoutState` TINYINT NOT NULL DEFAULT 0 AFTER `checkoutTime`;

-- 资金流添加交易对象字段
ALTER TABLE `db_lili`.`t_u_money` ADD COLUMN `tranObject` VARCHAR(50) NULL AFTER `isBalance`;

