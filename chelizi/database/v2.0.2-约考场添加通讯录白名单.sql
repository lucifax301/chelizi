-- 约考场教练白名单
DROP TABLE IF EXISTS `t_exam_place_whitelist`;
CREATE TABLE `t_exam_place_whitelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(50) DEFAULT NULL COMMENT '教练姓名',
  `mobile` varchar(30) DEFAULT NULL COMMENT '教练手机号（可能有多个）',
  `school` varchar(20) DEFAULT NULL COMMENT '所属驾校',
  `schoolId` int(10) DEFAULT NULL COMMENT '所属驾校id',
  `state` tinyint(4) DEFAULT '0' COMMENT '是否已删除：0-未删除，启用中；1-已删除',
  `ctime` datetime DEFAULT NULL COMMENT '创建时间',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `extra` varchar(200) DEFAULT NULL COMMENT '冗余',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='约考场教练白名单';