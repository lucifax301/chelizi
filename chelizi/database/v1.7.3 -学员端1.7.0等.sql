-- 添加大客户
drop TABLE if EXISTS t_u_student_vip;
CREATE TABLE `t_u_student_vip` (
  `vipId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `vipName` varchar(50) DEFAULT NULL COMMENT '大客户全称',
  `vipNickName` varchar(50) DEFAULT NULL COMMENT '标识简称',
  `vipCustomerManager` varchar(50) DEFAULT NULL COMMENT '客户经理',
  `vipContactPhone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `vipProtocol` text COMMENT '协议',
  `vipPrivilege` text COMMENT '优惠政策',
  `citys` varchar(255) DEFAULT NULL COMMENT '地区，多个用逗号隔开',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `ctime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(255) DEFAULT NULL COMMENT '冗余',
  `chargeDiscountTmpId` varchar(32) DEFAULT NULL COMMENT '充值优惠的模板id',
  PRIMARY KEY (`vipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 学员表添加大客户标识
ALTER TABLE t_u_student
ADD `vipId` int(11) DEFAULT NULL COMMENT '大客户标识';

-- 添加对emoji支持
ALTER TABLE t_u_feedback MODIFY `fbcontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '评论内容';
ALTER TABLE t_u_feedback CHARSET=utf8mb4 COMMENT='用户评论记录';
-- 线上环境已经有了
-- ALTER TABLE t_stu_comment MODIFY `one_word` varchar(254) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '一句话评价';
-- ALTER TABLE t_coach_comment MODIFY `one_word` varchar(254) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '一句话评价';


-- ----------------------------
--  Table structure for `t_s_ticket` 卡券信息表
-- ----------------------------
DROP TABLE IF EXISTS `t_s_ticket`;
CREATE TABLE `t_s_ticket` (
  `ticketId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id，用于记录',
  `ticketType` bit(1) NOT NULL DEFAULT b'1' COMMENT '卡券类型，1=QQ卡券',
  `couponTmpId` varchar(32) NOT NULL COMMENT '优惠券的模板id',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '卡券状态，0=未使用，1=已使用，-1=已废弃',
  `cardid` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expireTime` timestamp NULL DEFAULT NULL,
  `useTime` timestamp NULL DEFAULT NULL,
  `studentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ticketId`,`ticketType`),
  KEY `code_index` (`code`) USING BTREE,
  KEY `cardid_index` (`cardid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='卡券存储表';

-- ----------------------------
--  Table structure for `t_s_charge_discount` 大客户充值优惠信息的表
-- ----------------------------
DROP TABLE IF EXISTS `t_s_charge_discount`;
CREATE TABLE `t_s_charge_discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chargeDiscountTmpId` varchar(32) NOT NULL COMMENT '模板id，同一个模板id的数据，为一组',
  `limitMoney` int(11) NOT NULL DEFAULT '0' COMMENT '表示使用该优惠时的最低金额',
  `discount` int(11) NOT NULL COMMENT '所送的金额数，单位为分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;



-- 课程添加文科题
INSERT INTO `t_s_coursenew` VALUES
('W10001', '文科', 8, '文科', '文科', '文科', 1, '100100,100101,100102,100103,103100,102100', '文科题C1', NULL, '1', 1, NULL, 17000, 17000, NULL, NULL, '1,2,3', 0),
('W20001', '文科', 18, '文科', '文科', '文科', 1, '100100,100101,100102,100103,103100,102100', '文科题C2', NULL, '1', 2, NULL, 17000, 17000, NULL, NULL, '1,2,3', 0);




