
-- 报名的佣金模板
DROP TABLE IF EXISTS `t_brokerage_enroll`;
CREATE TABLE `t_brokerage_enroll` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regionId` int(11) NOT NULL COMMENT '地区id',
  `schoolId` int(11) NOT NULL COMMENT '驾校Id；因为驾校太多，配置个0代表通用驾校',
  `brokerageType` tinyint(4) NOT NULL COMMENT '佣金类型，1=比例，2=具体金额',
  `brokerage` int(11) NOT NULL DEFAULT '0' COMMENT '佣金的数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='报名的佣金模板';

-- 初始化数据
INSERT INTO t_brokerage_enroll (regionId,schoolId,brokerageType,brokerage)
VALUES
(100100,0,2,15080), -- 深圳
(100101,0,2,15080), -- 东莞	这个也乱写的
(100102,0,2,15080), -- 广州
(100103,0,2,15080), -- 佛山
(101100,0,2,15080), -- 杭州
(102100,0,2,3190), -- 西安
(103100,0,2,3370); -- 长沙

-- 修改资金流水，添加代收款描述
ALTER TABLE t_u_money MODIFY COLUMN `isEarning` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否为收入：0-支出，1-收入，2-代收款';
ALTER TABLE t_u_money MODIFY COLUMN `orderId` varchar(100) DEFAULT NULL COMMENT '关联的订单id|优惠券id';

ALTER TABLE t_enroll_order MODIFY COLUMN `pay_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态：0未开始，1已提交，100支付成功，101支付失败';
ALTER TABLE t_enroll_order DROP COLUMN `checkoutState`;
ALTER TABLE t_enroll_order CHANGE COLUMN  `checkoutTime` `checkout_time` timestamp NULL DEFAULT NULL COMMENT '结款时间';
ALTER TABLE t_enroll_order ADD COLUMN `order_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态：0已取消，1已下单，2未结款，3已结款，4退款中，5已退款，6退款失败，';
ALTER TABLE t_enroll_order ADD COLUMN `brokerage` int(11) NOT NULL DEFAULT '0' COMMENT '结算佣金金额，单位：分';

-- 报名状态初始化数据
UPDATE t_enroll_order set order_state = 0 WHERE isdel =1;
UPDATE t_enroll_order set order_state = 2 WHERE isdel =0 and pay_state=100;

ALTER TABLE t_u_student MODIFY COLUMN `sex` tinyint(4) DEFAULT '1' COMMENT '性别(0,女；1，男；2，未知)';
ALTER TABLE t_u_student MODIFY COLUMN `drType` tinyint(4) DEFAULT '1' COMMENT '驾驶类型（1代表C1,2代表C2,3代表其它）';




