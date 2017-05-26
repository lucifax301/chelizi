-- ��Ӵ�ͻ�
drop TABLE if EXISTS t_u_student_vip;
CREATE TABLE `t_u_student_vip` (
  `vipId` int(11) NOT NULL AUTO_INCREMENT COMMENT '��������',
  `vipName` varchar(50) DEFAULT NULL COMMENT '��ͻ�ȫ��',
  `vipNickName` varchar(50) DEFAULT NULL COMMENT '��ʶ���',
  `vipCustomerManager` varchar(50) DEFAULT NULL COMMENT '�ͻ�����',
  `vipContactPhone` varchar(50) DEFAULT NULL COMMENT '��ϵ�绰',
  `vipProtocol` text COMMENT 'Э��',
  `vipPrivilege` text COMMENT '�Ż�����',
  `citys` varchar(255) DEFAULT NULL COMMENT '����������ö��Ÿ���',
  `operator` varchar(50) DEFAULT NULL COMMENT '������',
  `ctime` timestamp NULL DEFAULT NULL COMMENT '����ʱ��',
  `mtime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `extra` varchar(255) DEFAULT NULL COMMENT '����',
  `chargeDiscountTmpId` varchar(32) DEFAULT NULL COMMENT '��ֵ�Żݵ�ģ��id',
  PRIMARY KEY (`vipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ѧԱ����Ӵ�ͻ���ʶ
ALTER TABLE t_u_student
ADD `vipId` int(11) DEFAULT NULL COMMENT '��ͻ���ʶ';

-- ��Ӷ�emoji֧��
ALTER TABLE t_u_feedback MODIFY `fbcontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '��������';
ALTER TABLE t_u_feedback CHARSET=utf8mb4 COMMENT='�û����ۼ�¼';
-- ���ϻ����Ѿ�����
-- ALTER TABLE t_stu_comment MODIFY `one_word` varchar(254) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'һ�仰����';
-- ALTER TABLE t_coach_comment MODIFY `one_word` varchar(254) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'һ�仰����';


-- ----------------------------
--  Table structure for `t_s_ticket` ��ȯ��Ϣ��
-- ----------------------------
DROP TABLE IF EXISTS `t_s_ticket`;
CREATE TABLE `t_s_ticket` (
  `ticketId` int(11) NOT NULL AUTO_INCREMENT COMMENT '������id�����ڼ�¼',
  `ticketType` bit(1) NOT NULL DEFAULT b'1' COMMENT '��ȯ���ͣ�1=QQ��ȯ',
  `couponTmpId` varchar(32) NOT NULL COMMENT '�Ż�ȯ��ģ��id',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '��ȯ״̬��0=δʹ�ã�1=��ʹ�ã�-1=�ѷ���',
  `cardid` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expireTime` timestamp NULL DEFAULT NULL,
  `useTime` timestamp NULL DEFAULT NULL,
  `studentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ticketId`,`ticketType`),
  KEY `code_index` (`code`) USING BTREE,
  KEY `cardid_index` (`cardid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='��ȯ�洢��';

-- ----------------------------
--  Table structure for `t_s_charge_discount` ��ͻ���ֵ�Ż���Ϣ�ı�
-- ----------------------------
DROP TABLE IF EXISTS `t_s_charge_discount`;
CREATE TABLE `t_s_charge_discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chargeDiscountTmpId` varchar(32) NOT NULL COMMENT 'ģ��id��ͬһ��ģ��id�����ݣ�Ϊһ��',
  `limitMoney` int(11) NOT NULL DEFAULT '0' COMMENT '��ʾʹ�ø��Ż�ʱ����ͽ��',
  `discount` int(11) NOT NULL COMMENT '���͵Ľ��������λΪ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;



-- �γ�����Ŀ���
INSERT INTO `t_s_coursenew` VALUES
('W10001', '�Ŀ�', 8, '�Ŀ�', '�Ŀ�', '�Ŀ�', 1, '100100,100101,100102,100103,103100,102100', '�Ŀ���C1', NULL, '1', 1, NULL, 17000, 17000, NULL, NULL, '1,2,3', 0),
('W20001', '�Ŀ�', 18, '�Ŀ�', '�Ŀ�', '�Ŀ�', 1, '100100,100101,100102,100103,103100,102100', '�Ŀ���C2', NULL, '1', 2, NULL, 17000, 17000, NULL, NULL, '1,2,3', 0);




