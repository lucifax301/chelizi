alter table t_enroll_order add column channel tinyint(4) NOT NULL DEFAULT '1' COMMENT '����������1-��ʱ���2-��У���';


CREATE TABLE `t_enroll_purpose` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `student_id` bigint(20) NOT NULL COMMENT 'ѧԱID',
  `name` varchar(32) DEFAULT NULL COMMENT 'ѧԱ����',
  `phone_num` varchar(64) DEFAULT NULL COMMENT '�ֻ���',
  `head_icon` varchar(256) DEFAULT NULL COMMENT 'ͷ��',
  `channel` tinyint(4) NOT NULL DEFAULT '1' COMMENT '������Դ��1-�Ҽ�У��2-�ҽ���',
  `ttid` int(11) DEFAULT NULL COMMENT '������ID',
  `class_id` int(11) DEFAULT NULL COMMENT '���ID',
  `city_id` int(11) DEFAULT NULL COMMENT '����id',
  `school_id` int(11) DEFAULT NULL COMMENT '��Уid',
  `coach_id` bigint(20) DEFAULT NULL COMMENT '����id',
  `package_name` varchar(50) DEFAULT NULL COMMENT '����ģ�������',
  `market_price` int(11) DEFAULT NULL COMMENT '�г��ۣ���λ��',
  `price` int(11) DEFAULT NULL COMMENT '�����ܼۣ���λ��',
  `isdel` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ��״̬��0����δɾ����1������ɾ��',
  `cuid` bigint(20) DEFAULT NULL COMMENT '������id',
  `muid` bigint(20) DEFAULT NULL COMMENT '������id',
  `ctime` datetime DEFAULT NULL COMMENT '����ʱ��',
  `mtime` timestamp NULL DEFAULT NULL COMMENT '����ʱ��',
  `extra` varchar(255) DEFAULT NULL COMMENT '����',
  `cType` int(2) DEFAULT '0' COMMENT 'ѧ������ 1:C1  2 C2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='���������';


update `t_wechat_enroll_school` set score=5 where score=8;

update `t_s_school` set score=5 where score=8;