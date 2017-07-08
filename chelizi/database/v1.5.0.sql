-- �޸��������
DELETE FROM t_comment_tag WHERE ctid = 39;
DELETE FROM t_skill_relation WHERE ctid = 39;
UPDATE t_comment_tag SET tag = '��ʻϰ��' WHERE ctid = 36;
UPDATE t_comment_tag SET tag = '����״��' WHERE ctid = 37;
UPDATE t_comment_tag SET tag = '��·�о�' WHERE ctid = 38;


-- 20160225
--  ------------- ����ֶθ��� -----------------
ALTER TABLE t_p_notice ADD COLUMN extra varchar(254) DEFAULT NULL COMMENT '�����ֶΣ�����json��ʽ����url����Ϣ';
ALTER TABLE t_u_student ADD COLUMN flowNo varchar(254) DEFAULT NULL COMMENT 'ѧԱ������ˮ��';
ALTER TABLE t_enroll_order ADD COLUMN ctime datetime DEFAULT NULL COMMENT '����ʱ��';
ALTER TABLE t_enroll_order ADD COLUMN mtime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��';
-- ���еĶ�����ʼ��һ��ʱ��
UPDATE t_enroll_order SET ctime = NOW();

-- �������
DROP TABLE IF EXISTS t_u_poster;
CREATE TABLE `t_u_poster` (
  `posterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '��������',
  `posterName` varchar(254) DEFAULT NULL COMMENT '�������',
  `posterPic` varchar(254) DEFAULT NULL COMMENT '���ͼƬ���� �ֱ��ʸ�',
  `posterPic2` varchar(254) DEFAULT NULL COMMENT '���ͼƬ����2 �ֱ�����',
  `posterPic3` varchar(254) DEFAULT NULL COMMENT '���ͼƬ����3 �ֱ��ʵ�',
  `posterUrl` varchar(255) DEFAULT NULL COMMENT '�������תurl',
  `extra` varchar(255) DEFAULT NULL COMMENT '�����ֶ�',
  `ctime` datetime DEFAULT NULL COMMENT '����ʱ��',
  `mtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`posterId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- �¿γ�
DROP TABLE IF EXISTS t_s_coursenew;
CREATE TABLE `t_s_coursenew` (
  `coursenewNo` varchar(30) NOT NULL COMMENT '�γ̱��',
  `coursenewName` varchar(30) DEFAULT NULL COMMENT '�γ�����',
  `courseTemId` int(10) DEFAULT NULL COMMENT '�γ�id ��ԭid��Ӧ',
  `level1` varchar(30) DEFAULT NULL COMMENT 'һ������',
  `level2` varchar(30) DEFAULT NULL COMMENT '��������',
  `subject` varchar(30) DEFAULT NULL COMMENT '���Կ�Ŀ',
  `subjectId` int(10) DEFAULT NULL COMMENT '��Ŀid',
  `citys` varchar(30) DEFAULT NULL COMMENT '���ó���',
  `descripton` varchar(255) DEFAULT NULL COMMENT '����',
  `content` varchar(255) DEFAULT NULL COMMENT '��ѧ����',
  `period` varchar(30) DEFAULT NULL COMMENT '����ѧʱ',
  `drType` tinyint(4) DEFAULT NULL COMMENT '��ʻ���ͣ�1����C1,2����C2,3����������',
  `tags` varchar(30) DEFAULT NULL COMMENT '���ܵ㣬����ö��Ÿ���',
  `price1` int(11) DEFAULT NULL COMMENT '�����ռ۸�',
  `price2` int(11) DEFAULT NULL COMMENT '��ĩ�۸�',
  `remark` varchar(255) DEFAULT NULL COMMENT '��ע',
  `extra` varchar(255) DEFAULT NULL COMMENT '����',
  PRIMARY KEY (`coursenewNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- �¿γ���������
INSERT INTO `t_s_coursenew` VALUES 
('E10001', '��Ŀ������ѵ��', 1, '��Ŀ��', '����ѵ��', '��Ŀ��', 2, NULL, '����ɿ�Ŀһ�����޿�Ŀ����ϰ������ѧԱ', NULL, '6-8', 1, NULL, 17000, 19000, NULL, NULL),
('E10002', '��Ŀ��Ӧ��ѵ��', 6, '��Ŀ��', 'Ӧ��ѵ��', '��Ŀ��', 2, NULL, '1���ѽ��в�����4Сʱ�Ŀ�Ŀ������ѵ����\r\n2��׼��ԤԼ��Ŀ�����Ե���ѧѧԱ', NULL, '4-6', 1, NULL, 17000, 19000, NULL, NULL),
('E10003', '��Ŀ������ģ��', 2, '��Ŀ��', '����ģ��', '��Ŀ��', 2, NULL, '1���Ѿ�ԤԼ�ɹ���Ŀ�����Ե�ѧԱ��\r\n2����2���ڿ��Կ�Ŀ����ѧԱ', NULL, '1-2', 1, NULL, 20000, 22000, NULL, NULL),
('E20001', '��Ŀ������ѵ��', 11, '��Ŀ��', '����ѵ��', '��Ŀ��', 2, NULL, '����ɿ�Ŀһ�����޿�Ŀ����ϰ������ѧԱ', NULL, '6-8', 2, NULL, 18500, 20500, NULL, NULL),
('E20002', '��Ŀ��Ӧ��ѵ��', 16, '��Ŀ��', 'Ӧ��ѵ��', '��Ŀ��', 2, NULL, '1���ѽ��в�����4Сʱ�Ŀ�Ŀ������ѵ����\r\n2��׼��ԤԼ��Ŀ�����Ե���ѧѧԱ', NULL, '4-6', 2, NULL, 18500, 20500, NULL, NULL),
('E20003', '��Ŀ������ģ��', 12, '��Ŀ��', '����ģ��', '��Ŀ��', 2, NULL, '1���Ѿ�ԤԼ�ɹ���Ŀ�����Ե�ѧԱ��\r\n2����2���ڿ��Կ�Ŀ����ѧԱ', NULL, '1-2', 2, NULL, 20500, 23500, NULL, NULL),
('S10001', '��Ŀ������ѵ��', 3, '��Ŀ��', '����ѵ��', '��Ŀ��', 3, NULL, '����ɿ�Ŀ�������޿�Ŀ����ϰ������ѧԱ', NULL, '2', 1, NULL, 17000, 19000, NULL, NULL),
('S10002', '��Ŀ��Ӧ��ѵ��', 7, '��Ŀ��', 'Ӧ��ѵ��', '��Ŀ��', 3, NULL, '1���ѽ��в�����2Сʱ�Ŀ�Ŀ������ѵ����\r\n2��׼��ԤԼ��Ŀ�����Ե���ѧѧԱ', NULL, '4-6', 1, NULL, 17000, 19000, NULL, NULL),
('S10003', '��Ŀ������ģ��', 4, '��Ŀ��', '����ģ��', '��Ŀ��', 3, NULL, '1���Ѿ�ԤԼ�ɹ���Ŀ�����Ե�ѧԱ��\r\n2����2���ڿ��Կ�Ŀ����ѧԱ', NULL, '1-2', 1, NULL, 20000, 22000, NULL, NULL),
('S20001', '��Ŀ������ѵ��', 13, '��Ŀ��', '����ѵ��', '��Ŀ��', 3, NULL, '����ɿ�Ŀ�������޿�Ŀ����ϰ������ѧԱ', NULL, '2', 2, NULL, 18500, 20500, NULL, NULL),
('S20002', '��Ŀ��Ӧ��ѵ��', 17, '��Ŀ��', 'Ӧ��ѵ��', '��Ŀ��', 3, NULL, '1���ѽ��в�����2Сʱ�Ŀ�Ŀ������ѵ����\r\n2��׼��ԤԼ��Ŀ�����Ե���ѧѧԱ', NULL, '4-6', 2, NULL, 18500, 20500, NULL, NULL),
('S20003', '��Ŀ������ģ��', 14, '��Ŀ��', '����ģ��', '��Ŀ��', 3, NULL, '1���Ѿ�ԤԼ�ɹ���Ŀ�����Ե�ѧԱ��\r\n2����2���ڿ��Կ�Ŀ����ѧԱ', NULL, '1-2', 2, NULL, 20500, 23500, NULL, NULL),
('P10001', '���', 5, '���', '���', '���', 5, NULL, '1�����õ����գ�ԾԾ���Ե����ֵ���\r\n2����Ӧ������������������ٿ������е�ѷ��\r\n3���ص��һ��ȥ��ͣ��ͣ���죬�ؼ���ͣ����ȥ��', '1�����ﲻ��ѧ��������ϴ������ǿ��ѵ��������Ϊֹ��\r\n2���������������ᳵ�������ͣ���������������ѧɶ��ѧɶ��', '4-6', 1, NULL, 17000, 17000, NULL, NULL),
('P20001', '���', 15, '���', '���', '���', 5, NULL, '1�����õ����գ�ԾԾ���Ե����ֵ���\r\n2����Ӧ������������������ٿ������е�ѷ��\r\n3���ص��һ��ȥ��ͣ��ͣ���죬�ؼ���ͣ����ȥ��', '1�����ﲻ��ѧ��������ϴ������ǿ��ѵ��������Ϊֹ��\r\n2���������������ᳵ�������ͣ���������������ѧɶ��ѧɶ��', '4-6', 2, NULL, 20000, 20000, NULL, NULL);


-- �û�������¼
DROP TABLE IF EXISTS t_u_feedback;
CREATE TABLE `t_u_feedback` (
  `fbid` int(20) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `fbtitle` varchar(100) DEFAULT NULL COMMENT '����',
  `fbcontent` text COMMENT '����',
  `fbpicture` varchar(255) DEFAULT NULL COMMENT 'ͼƬURL',
  `userid` bigint(32) DEFAULT NULL COMMENT '�û�id',
  `usertype` tinyint(16) DEFAULT NULL COMMENT '�û�����',
  `username` varchar(30) DEFAULT NULL COMMENT '�û�����',
  `userphone` varchar(32) DEFAULT NULL COMMENT '�û��ֻ���',
  `ctime` datetime DEFAULT NULL COMMENT '����ʱ��',
  `mtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
  `state` tinyint(4) DEFAULT '0' COMMENT '״̬��0-�·�����1-�Ѵ���',
  `extra` varchar(255) DEFAULT NULL COMMENT '����',
  PRIMARY KEY (`fbid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- �������۱�ǩ
DROP TABLE IF EXISTS t_comment_tag;
CREATE TABLE `t_comment_tag` (
  `ctid` int(11) NOT NULL AUTO_INCREMENT COMMENT '�۸������',
  `tag` varchar(50) DEFAULT NULL COMMENT '��ǩ',
  `course_id` int(11) DEFAULT NULL COMMENT '���id�������ѧ���ı�ǩ��Ϊ�ݿ����ͣ�1ΪC1,2ΪC2������ڽ����ı�ǩ��Ϊ������',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '��ǩ���ͣ�1���������ı�ǩ��2����ѧԱ�ı�ǩ',
  `good_bad` tinyint(4) DEFAULT NULL COMMENT '�����������ࣺ1����������0����������ֻ�н�����ǩ��ֵ',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ��״̬��0����δɾ����1������ɾ��',
  `cuid` int(11) DEFAULT NULL COMMENT '������id',
  `muid` int(11) DEFAULT NULL COMMENT '������id',
  `ctime` datetime DEFAULT NULL COMMENT '����ʱ��',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`ctid`),
  UNIQUE KEY `ctid` (`ctid`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='���۱�ǩ��';

INSERT INTO `t_comment_tag` VALUES
(1, '��ѧʱ�β�����', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(2, '����̬�Ⱥܲ�', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(3, '��Ϊ��ֹ�����', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(4, '��������������', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(5, '����ʱ', 1, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(6, '��ѧ�е��������', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(7, '��ѧʱ��С��', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(8, '�������', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(9, '������ע���������', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(10, 'ʱ������', 2, 1, 0, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(11, '��ѧ����������', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(12, '����Ա�Ƚ�ע�ظ�������', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(13, '����ԤԼ��ѧ�γ̽��н�ѧ', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(14, '��ѧ̬������', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(15, '����ָ�������ʱش�', 3, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(16, '�������Ľ�ѧ', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(17, '��������̬��Ҳ�ܺ�', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(18, '��ע�ظ�������', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(19, 'ʱ�����ǿ', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(20, '��ѧ���治��С��', 4, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(21, '�������Ĵ��ڿ��Թ���ϸ�ڡ������������������', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(22, '���������渺��', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(23, '������ϵѧԱ', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(24, '��װרҵ�����ࡢ����ʱ������', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(25, '�������ܺ�', 5, 1, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(26, '�������', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(27, '�෽ͣ��', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(28, '�µ�����', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(29, 'ֱ��ת��', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(30, '������ʻ', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2015-10-23 00:00:00'),
(31, '��·��ʻ', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:50:59'),
(32, 'ͨ��·��', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:51:00'),
(33, 'ͨ���ض�����', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:51:01'),
(34, 'ҹ����ʻ', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:51:02'),
(35, '�ۺ�����', 1, 2, 1, 1, 0, 0, '2015-10-23 00:00:00', '2016-2-25 17:51:04'),
(36, '��ʻϰ��', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2016-2-1 17:20:40'),
(37, '����״��', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2016-2-1 17:20:40'),
(38, '��·�о�', 1, 2, 1, 0, 0, 0, '2015-10-23 00:00:00', '2016-2-1 17:20:40'),
(50, '��Ŀ��Ӧ��ѵ��', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:17'),
(51, '��Ŀ������ģ��', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:21'),
(52, '��Ŀ������ѵ��', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:13'),
(53, '��Ŀ��Ӧ��ѵ��', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:01'),
(54, '��Ŀ������ģ��', 1, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:06'),
(57, '��ʻϰ��', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(58, '����״��', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(59, '��·�о�', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(60, '�������', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(61, '�෽ͣ��', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(62, '�µ�����', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(63, 'ֱ��ת��', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(64, '������ʻ', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(65, '��Ŀ��Ӧ��ѵ��', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:32'),
(66, '��Ŀ������ģ��', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:37'),
(67, '��Ŀ������ѵ��', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:28:28'),
(68, '��Ŀ��Ӧ��ѵ��', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:46:07'),
(69, '��Ŀ������ģ��', 2, 2, 1, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 17:46:04');


-- ���¼��ܵ�
DROP TABLE IF EXISTS t_skill_relation;
CREATE TABLE `t_skill_relation` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `ctid` int(11) NOT NULL COMMENT '���ܱ�ǩid',
  `city_id` int(11) DEFAULT NULL COMMENT '�ü�������ĳ���',
  `course_id` int(11) DEFAULT NULL COMMENT '�ü��ܵĿγ�',
  `subject_id` int(11) DEFAULT NULL COMMENT '�ü��ܵĿ�Ŀ',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ��״̬��0����δɾ����1������ɾ��',
  `cuid` int(11) DEFAULT NULL COMMENT '������id',
  `muid` int(11) DEFAULT NULL COMMENT '������id',
  `ctime` datetime DEFAULT NULL COMMENT '����ʱ��',
  `mtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sid` (`sid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='���γ̼��ܵ��ϵ��';

INSERT INTO `t_skill_relation` VALUES 
(30, 36, 0, 5, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(31, 57, 0, 15, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:56:57'),
(32, 37, 0, 5, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(33, 58, 0, 15, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:00'),
(34, 38, 0, 5, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(35, 59, 0, 15, 5, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:02'),
(36, 26, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(37, 60, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:03'),
(38, 27, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(39, 61, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:04'),
(40, 28, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(41, 62, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:05'),
(42, 29, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(43, 63, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:05'),
(44, 30, 0, 1, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(45, 64, 0, 11, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:06'),
(46, 50, 0, 6, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(47, 65, 0, 16, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:08'),
(48, 51, 0, 2, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(49, 66, 0, 12, 2, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:09'),
(50, 52, 0, 3, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(51, 67, 0, 13, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:10'),
(52, 53, 0, 7, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(53, 68, 0, 17, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:11'),
(54, 54, 0, 4, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-25 00:00:00'),
(55, 69, 0, 14, 3, 0, 0, 0, '2016-2-25 00:00:00', '2016-2-29 16:57:13');

-- ����ѧԱ�ݿ�����
update t_u_student set applyCarType = '1' WHERE applyCarType = '0' or applyCarType = '3' or applyCarType ='';
update t_u_student set drtype = CAST(applyCarType AS signed) WHERE drType is null;
ALTER TABLE t_u_student MODIFY COLUMN applyCarType varchar(4) DEFAULT '1' COMMENT '����ĳ�������(1����C1,2����C2)';










