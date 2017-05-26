-- 优惠方案
-- 优惠方案档位
-- 优惠方案适用驾校
-- 优惠方案充值记录

DROP TABLE IF EXISTS `t_exam_place_recharge`;
CREATE TABLE `t_exam_place_recharge` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(30) DEFAULT NULL COMMENT '优化方案名称',
  `schools` varchar(255) DEFAULT NULL COMMENT '优惠可使用的驾校id，多个用逗号隔开',
  `schoolCount` int(5) DEFAULT '0' COMMENT '驾校数量',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态：0-已激活；1-已停用；2-已删除',
  `info` varchar(200) DEFAULT NULL COMMENT '充值规则说明',
  `cuser` varchar(30) DEFAULT NULL COMMENT '创建人',
  `muser` varchar(30) DEFAULT NULL COMMENT '修改人',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='优惠方案';

DROP TABLE IF EXISTS `t_exam_place_recharge_gears`;
CREATE TABLE `t_exam_place_recharge_gears` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `rid` int(11) NOT NULL COMMENT '关联优惠方案id',
  `min` int(11) DEFAULT '0' COMMENT '档位最小金额',
  `max` int(11) DEFAULT '0' COMMENT '档位最大金额',
  `favor` int(11) DEFAULT '0' COMMENT '赠送金额',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态：0-已激活；1-已停用；2-已删除',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '优惠方案档位';

DROP TABLE IF EXISTS `t_exam_place_recharge_school`;
CREATE TABLE `t_exam_place_recharge_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `rid` int(11) NOT NULL COMMENT '关联优惠方案id',
  `schoolId` int(11) DEFAULT '0' COMMENT '驾校id',
  `schoolName` varchar(30) DEFAULT NULL COMMENT '驾校名称',
  `schoolMobile` varchar(30) DEFAULT NULL COMMENT '驾校客服电话',
  `schoolAddr` varchar(30) DEFAULT NULL COMMENT '驾校地址',
  `coachCount` int(11) DEFAULT '0' COMMENT '教练人数',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态：0-已激活；1-已停用；2-已删除',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`),
  UNIQUE key(rid,schoolId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '优惠方案适用驾校';


DROP TABLE IF EXISTS `t_exam_place_recharge_record`;
CREATE TABLE `t_exam_place_recharge_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `orderId` varchar(32) NOT NULL COMMENT '订单号',
  `rid` int(11) NOT NULL COMMENT '关联优惠方案id',
  `rname` varchar(30) DEFAULT NULL COMMENT '优化方案名称',
  `schoolId` int(11) DEFAULT '0' COMMENT '驾校id',
  `schoolName` varchar(30) DEFAULT NULL COMMENT '驾校名称',
  `channel` varchar(10) DEFAULT NULL COMMENT '充值渠道',
  `money` int(11) DEFAULT '0' COMMENT '充值金额',
  `favor` int(11) DEFAULT '0' COMMENT '赠送金额',
  `total` int(11) DEFAULT '0' COMMENT '到账总金额',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态：0-已激活；1-已停用；2-已删除',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`),
  key(`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '优惠方案充值记录';




-- 驾校充值用于约考场等，设置专用金额的限额
ALTER TABLE t_s_schaccount ADD COLUMN examPlaceMax int(11) DEFAULT '0' COMMENT '约考场限额';
ALTER TABLE t_s_schaccount ADD COLUMN examPlaceNow int(11) DEFAULT '0' COMMENT '约考场金额';
ALTER TABLE t_s_schaccount ADD COLUMN extra varchar(255) DEFAULT NULL COMMENT '冗余';


-- 20161111约考场排班支持多种优惠方案
ALTER TABLE t_exam_place_class MODIFY COLUMN favorType int(4) DEFAULT '0' COMMENT '优惠类型：0-不优惠；1-返课时；2-返金额';

-- 20161114约考场添加索引
-- ALTER TABLE t_exam_place_order ADD INDEX idx (coachId);
-- ALTER TABLE t_exam_place_order ADD INDEX idx1 (pstart);
-- ALTER TABLE t_exam_place_class ADD INDEX idx2 (pstart);

-- 添加订单优惠方式
ALTER TABLE t_exam_place_order ADD COLUMN favorType int(4) DEFAULT '1' COMMENT '优惠类型：0-不优惠；1-返课时；2-返金额' AFTER favorInfo;
ALTER TABLE t_exam_place_class MODIFY COLUMN favorIn int(11) DEFAULT '0' COMMENT '累计几个小时';
ALTER TABLE t_exam_place_class MODIFY COLUMN favorOut int(11) DEFAULT '0' COMMENT '送的学时或金额';












