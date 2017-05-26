-- 学员添加优惠套餐的字段
ALTER TABLE `db_lili`.`t_u_student` ADD COLUMN `vipPackageId` VARCHAR(32) NULL DEFAULT NULL AFTER `reviveTime`;

-- 大客户充值优惠的表
DROP TABLE IF EXISTS `t_s_vip_charge_discount`;
CREATE TABLE `t_s_vip_charge_discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vipPackageId` varchar(32) NOT NULL COMMENT '模板id，同一个模板id的数据，为一组',
  `limitMoney` int(11) NOT NULL DEFAULT '0' COMMENT '表示使用该优惠时的最低金额',
  `discount` int(11) NOT NULL COMMENT '所送的金额数，单位为分',
  `expireTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- 充值送金额的数据
INSERT INTO `t_s_vip_charge_discount` VALUES
  (1,'4d6e7c3da6fd424c845b729e0149a533',50000,5000,NULL),
  (2,'4d6e7c3da6fd424c845b729e0149a533',100000,15000,NULL),
  (3,'4d6e7c3da6fd424c845b729e0149a533',150000,30000,NULL),
  (4,'4d6e7c3da6fd424c845b729e0149a533',200000,50000,NULL),
  (5,'4d6e7c3da6fd424c845b729e0149a533',250000,75000,NULL),
  (6,'4d6e7c3da6fd424c845b729e0149a533',300000,120000,NULL),
  (7,'5758aad917144255ae0b5bd1c78bf96e',100000,50000,'2016-06-30 15:59:59'),
  (8,'5758aad917144255ae0b5bd1c78bf96e',150000,75000,'2016-06-30 15:59:59'),
  (9,'5758aad917144255ae0b5bd1c78bf96e',200000,100000,'2016-06-30 15:59:59'),
  (10,'5758aad917144255ae0b5bd1c78bf96e',250000,125000,'2016-06-30 15:59:59'),
  (11,'5758aad917144255ae0b5bd1c78bf96e',300000,300000,'2016-06-30 15:59:59'),
  (12,'5758aad917144255ae0b5bd1c78bf96e',350000,350000,'2016-06-30 15:59:59'),
  (13,'5758aad917144255ae0b5bd1c78bf96e',400000,400000,'2016-06-30 15:59:59');

-- 大客户优惠券的表
DROP TABLE IF EXISTS `t_s_vip_coupon`;
CREATE TABLE `t_s_vip_coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vipPackageId` varchar(32) NOT NULL,
  `couponTmpId` varchar(32) NOT NULL,
  `expireTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- 插入套餐与优惠券的关联关系
INSERT INTO `t_s_vip_coupon` VALUES (1,'4d6e7c3da6fd424c845b729e0149a533','fd1642581f95449692f215cd96683f82',NULL);
-- 给对应的优惠券添加10000库存
INSERT INTO `db_lili`.`t_s_cstock` (`total`, `haveUsed`, `createTime`, `createUser`, `isExist`, `couponTempId`)
  VALUES ('10000', '0', '2016-06-14 10:30:00', 'zhou long', '1', 'fd1642581f95449692f215cd96683f82');

-- 大客户套餐表
DROP TABLE IF EXISTS `t_s_vip_package`;
CREATE TABLE `t_s_vip_package` (
  `id` varchar(32) NOT NULL,
  `isValid` tinyint(4) NOT NULL DEFAULT '1',
  `isDisableEnrollCoupon` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否禁止使用报名优惠券的限制',
  `extra` varchar(500) DEFAULT NULL,
  `expireTime` timestamp NULL DEFAULT NULL COMMENT 'VIP优惠套餐表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入大客户套餐的数据
INSERT INTO `t_s_vip_package` VALUES
  ('4d6e7c3da6fd424c845b729e0149a533',1,0,'{\"version\":\"20160614_1\",\"detail_url\":\"www.baidu.com\",\"enroll_msg\":\"减免两百元报名费\",\"charge_discount_msg\":\"充3000送1200\"}',NULL),
  ('5758aad917144255ae0b5bd1c78bf96e',1,0,'{\"version\":\"20160614_2\",\"detail_url\":\"www.baidu.com\",\"charge_discount_msg\":\"充3000送3000\",\"expireTime\":\"2016-06-20 00:00:00\"}','2016-06-30 15:59:59');


