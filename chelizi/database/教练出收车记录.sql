CREATE TABLE `t_coach_status_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `coachId` bigint(20) DEFAULT NULL COMMENT '冗余：教练id',
  `carId` int DEFAULT NULL COMMENT '冗余：车id',
  `lat` varchar(16) DEFAULT NULL,
  `lon` varchar(16) DEFAULT NULL,
  `status` smallint DEFAULT -1 COMMENT '1：出车;0:收车；',
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '动作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出收车日志';