CREATE TABLE `t_coach_status_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `coachId` bigint(20) DEFAULT NULL COMMENT '���ࣺ����id',
  `carId` int DEFAULT NULL COMMENT '���ࣺ��id',
  `lat` varchar(16) DEFAULT NULL,
  `lon` varchar(16) DEFAULT NULL,
  `status` smallint DEFAULT -1 COMMENT '1������;0:�ճ���',
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='���ճ���־';