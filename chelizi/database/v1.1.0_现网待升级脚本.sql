DROP TABLE
IF EXISTS `t_u_trfield_raw`;

CREATE TABLE `t_u_trfield_raw` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '��������',
  `name` varchar(255) DEFAULT NULL COMMENT 'ѵ��������',
  `address` varchar(255) DEFAULT NULL COMMENT 'ѵ������ַ',
  `school` varchar(255) DEFAULT NULL COMMENT 'ѵ����������У',
  `province` varchar(255) DEFAULT NULL COMMENT 'ʡ',
  `city` varchar(255) DEFAULT NULL COMMENT '��',
  `district` varchar(255) DEFAULT NULL COMMENT '��',
  `baiduaddr` varchar(255) DEFAULT NULL COMMENT '�ٶ��Զ���ȡ�ĵ�ַ',
  `lge` decimal(20,14) DEFAULT NULL,
  `lae` decimal(20,14) DEFAULT NULL,
  `mobile` varchar(30) DEFAULT NULL COMMENT '�ֻ���',
  `imei` varchar(255) DEFAULT NULL COMMENT 'Ψһ��ʾ',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ����϶������н����÷�Ϊ�յģ���Ҫ����Ĭ��ֵ
UPDATE t_order set coach_star = 80 WHERE coach_star is null;