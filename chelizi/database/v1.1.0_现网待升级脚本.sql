DROP TABLE
IF EXISTS `t_u_trfield_raw`;

CREATE TABLE `t_u_trfield_raw` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(255) DEFAULT NULL COMMENT '训练场名称',
  `address` varchar(255) DEFAULT NULL COMMENT '训练场地址',
  `school` varchar(255) DEFAULT NULL COMMENT '训练场所属驾校',
  `province` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `district` varchar(255) DEFAULT NULL COMMENT '区',
  `baiduaddr` varchar(255) DEFAULT NULL COMMENT '百度自动获取的地址',
  `lge` decimal(20,14) DEFAULT NULL,
  `lae` decimal(20,14) DEFAULT NULL,
  `mobile` varchar(30) DEFAULT NULL COMMENT '手机号',
  `imei` varchar(255) DEFAULT NULL COMMENT '唯一标示',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 如果老订单中有教练得分为空的，需要设置默认值
UPDATE t_order set coach_star = 80 WHERE coach_star is null;