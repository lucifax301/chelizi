alter table t_stu_comment add column subject_id int COMMENT '�����ֶΣ������۵Ŀ�Ŀ';

UPDATE `t_stu_comment` a , `t_skill_relation` b, t_order c SET a.course_id=c.course_id, a.subject_Id=b.Subject_id WHERE a.ctid=b.ctid AND  a.order_id=c.order_Id AND a.course_id=b.course_id;

alter table t_u_student add column drLicence varchar(32) COMMENT '���ձ��';
alter table t_u_student add column drPhoto varchar(32) COMMENT '����ͼƬ��ַ';
alter table t_u_student add column drType int COMMENT '��ʻ���ͣ�1����C1,2����C2,3����������';
alter table t_u_student add column agreement tinyint(4) default 0 COMMENT '�Ƿ�ͬ�����û�Э�� 0-δͬ�� 1-��ͬ��';
alter table t_u_student add column applyexam tinyint(4) default 0 COMMENT '����������״̬��0-δ������1-�ѱ����ɹ���2-���������';
alter table t_u_student add column pwstate tinyint(4) default 0 COMMENT '�Ƿ�������֧������- 0-δ���ã�1-������';

alter table t_u_coach add column agreement tinyint(4) default 0 COMMENT '�Ƿ�ͬ�����û�Э�� 0-δͬ�� 1-��ͬ��';
alter table t_u_coach add column pwstate tinyint(4) default 0 COMMENT '�Ƿ�������֧������- 0-δ���ã�1-������';

alter table t_u_coachaccount add column passwd varchar(64) COMMENT '֧������';
alter table t_u_stuaccount add column passwd varchar(64) COMMENT '֧������';
alter table t_u_money add column status tinyint(4) DEFAULT NULL COMMENT '״̬(1�ɹ���2ʧ��)';



CREATE TABLE `t_u_deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `waterId` varchar(32) NOT NULL COMMENT '��ˮID',
  `user_type` tinyint(4) NOT NULL COMMENT '�û����ͣ�1-������2-ѧԱ',
  `user_id` bigint(20) NOT NULL COMMENT '�û�id',
  `money` int(11) NOT NULL COMMENT '���ֽ���λ���֣�',
  `apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '��������ʱ��',
  `type` varchar(16) NOT NULL DEFAULT 'bank' COMMENT '��������: bank���п� wexin:΢�� zhifubao:֧���� qqwallet:QQǮ��',
  `verify_id` int(11) NOT NULL COMMENT '�������п��󶨱�ID',
  `order_id` varchar(32) NOT NULL COMMENT '����money��Orderid',
  `bank_card` varchar(32) NOT NULL COMMENT '���п���',
  `bank_name` varchar(32) DEFAULT NULL COMMENT '����������',
  `card_name` varchar(32) NOT NULL COMMENT '�ֿ�������',
  `checker` varchar(32) DEFAULT NULL COMMENT '������',
  `check_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '״̬,0-��������1-���ֳɹ���2-����ʧ��',
  `check_time` timestamp NULL DEFAULT NULL COMMENT '����ʱ��',
  `check_remark` varchar(128) DEFAULT NULL COMMENT '���ֲ���ԭ��',
  `remark` varchar(128) DEFAULT NULL COMMENT '��ע',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='������ϸ��';


CREATE TABLE `t_u_bankcard_verify` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Ψһid',
  `user_type` tinyint(4) NOT NULL COMMENT '�û����ͣ�1-������2-ѧԱ',
  `user_id` bigint(20) NOT NULL COMMENT '�û�id',
  `bank_no` varchar(32) NOT NULL COMMENT '���п���',
  `bank_name` varchar(32) NOT NULL COMMENT '������',
  `bank_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '���п����ͣ�0-�����1-���ÿ�',
  `card_bin` int(3) DEFAULT NULL COMMENT '���ÿ�bin',
  `name` varchar(32) NOT NULL COMMENT '�ֿ�������',
  `mobile` varchar(16) NOT NULL COMMENT '���а��ֻ���',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '״̬��0-��������1-��Ч���ţ�2-��Ч����',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `checker` varchar(32) DEFAULT NULL COMMENT '���������',
  `check_time` timestamp NULL DEFAULT NULL COMMENT '���ʱ��',
  `updater` varchar(32) DEFAULT NULL COMMENT '������',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '����ʱ��',
  `is_del` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ��״̬��0����δɾ����1������ɾ��',
  `remark` varchar(128) DEFAULT NULL COMMENT '��ע',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='���п�ʵ���󶨱�';



alter table t_order add column city_id  int COMMENT '�����ĳ���id';
alter table t_u_student add column cityId  int COMMENT 'ѧԱ��������id';
alter table t_s_school add column cityId  int COMMENT '��У���ڳ���id';
alter table t_p_car add column cityId  int COMMENT 'ѵ������������id';
alter table t_p_car add column schoolId  int default 1 COMMENT '��Уid';


